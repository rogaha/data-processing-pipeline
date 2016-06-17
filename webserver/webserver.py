#!/usr/bin/env python
import os
from time import sleep
import datetime
import logging, sys
from flask import jsonify, request

logging.basicConfig(stream=sys.stderr)

from flask import Flask, render_template, Response
from cassandra.cluster import Cluster
from cassandra.query import dict_factory

cluster = Cluster(['cassandra-1.weave.local'])
session = cluster.connect()


app = Flask(__name__)
app.logger.setLevel(logging.ERROR)


@app.route('/')
def index():
    return render_template('index.html')


@app.route('/v1/pulls/count')
def get_pulls_count():

    docker_pulls_v1 = session.execute("SELECT sum(count) as count from aggregated_metrics where action = 'repository_images_get' ALLOW FILTERING")
    docker_pulls_v2 = session.execute("SELECT sum(count) as count from aggregated_metrics where action = 'pull' ALLOW FILTERING")
    previous_count = session.execute("SELECT count from total_count where action = 'pulls' ORDER BY created_at DESC LIMIT 1")
    try:
        total = previous_count[0]['count'] + docker_pulls_v1[0]['count'] + docker_pulls_v2[0]['count']
    except:
        total = 0
    return str(dict(docker_pulls=total))


@app.route('/v1/pulls/count-unique-ips')
def get_unique_ip_pulls_count():
    aggregate_by_ips = {}
    results = session.execute("select distinct ip_address from docker_api_calls.aggregated_metrics")
    for item in results:
        aggregate_by_ips[item['ip_address']] = 1
    return str(dict(docker_pulls_unique_ips=len(aggregate_by_ips)))


@app.route('/v1/truncate/table')
def truncate_table():
    table_name =  request.args.get('name', None)
    result = False
    total = 0

    docker_pulls_v1 = session.execute("SELECT sum(count) as count from aggregated_metrics where action = 'repository_images_get' ALLOW FILTERING")
    docker_pulls_v2 = session.execute("SELECT sum(count) as count from aggregated_metrics where action = 'pull' ALLOW FILTERING")
    previous_count = session.execute("SELECT count from total_count where action = 'pulls' ORDER BY created_at DESC LIMIT 1")
    try:
        total = previous_count[0]['count'] + docker_pulls_v1[0]['count'] + docker_pulls_v2[0]['count']
    except Exception as e:
        pass
    if total and table_name:
        session.execute("INSERT INTO total_count (created_at, action,count) VALUES ( now(),'pulls', {0});".format(total))
        session.execute("TRUNCATE docker_api_calls.aggregated_metrics")
        result =  True
    return str(dict(result=result))


@app.route('/v1/pulls/coordinates')
def get_coordinates():
    lookback =  request.args.get('lookback', 10)

    date_time = datetime.datetime.now() - datetime.timedelta(seconds=int(lookback))
    date_str = date_time.strftime("%Y-%m-%d %H:%M:%S-0000")
    rows = session.execute("select action,ip_address,action_time ,city ,count ,country ,lat ,lon, region from aggregated_metrics where action_time >= maxTimeuuid('{0}') ALLOW FILTERING".format(date_str))
    return jsonify(results=[r for r in rows])

if __name__ == '__main__':
    # Define the cassandra keyspace
    session.set_keyspace('docker_api_calls')
    session.row_factory = dict_factory
    session.execute("CREATE TABLE IF NOT EXISTS total_count (created_at timeuuid, action text, count bigint, PRIMARY KEY (action, created_at))")
    # Start the webserver
    app.run(host='0.0.0.0', threaded=True)
