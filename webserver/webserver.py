#!/usr/bin/env python
import os
import datetime
import logging, sys
from flask.json import JSONEncoder
from flask import jsonify
from uuid import UUID

logging.basicConfig(stream=sys.stderr)

from flask import Flask, render_template, Response
from cassandra.cluster import Cluster
from cassandra.query import dict_factory

CASSANDRA_HOST_NODE = os.environ.get('CASSANDRA_HOST_NODE')
MINUTES_LOOCKBACK = os.environ.get('MINUTES_LOOCKBACK')

cluster = Cluster([CASSANDRA_HOST_NODE])
session = cluster.connect()

class UUIDEncoder(JSONEncoder):
    """ JSONEconder subclass used by the json render function.
    This is different from BaseJSONEoncoder since it also addresses
    encoding of UUID
    """

    def default(self, obj):
        if isinstance(obj, UUID):
            return str(obj)
        else:
            # delegate rendering to base class method (the base class
            # will properly render ObjectIds, datetimes, etc.)
            return super(UUIDEncoder, self).default(obj)


app = Flask(__name__)
app.json_encoder = UUIDEncoder

@app.route('/')
def index():
    return render_template('index_v2.html')

@app.route('/get_values')
def get_values():
    session.set_keyspace('twitter')
    session.row_factory = dict_factory
    date_time = datetime.datetime.now() - datetime.timedelta(minutes=MINUTES_LOOCKBACK)
    date_str = date_time.strftime("%Y-%m-%d %H:%M:%S-0700")
    rows = session.execute("select id, title,lat,lon, location, profile_image_url from tweets where id >= maxTimeuuid('{0}') and id_str = 'id_str'".format(date_str))
    return jsonify(results=rows)

if __name__ == '__main__':
    app.run()
