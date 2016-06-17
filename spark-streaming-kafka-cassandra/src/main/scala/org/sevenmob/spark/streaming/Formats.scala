package org.sevenmob.spark.streaming

import java.util.UUID


// Define the tweet class fields
case class Tweet(title:String,
                   retweets:BigInt,
                   favorites:BigInt,
                   location:String,
                   id: UUID,
                   lat: Double,
                   lon: Double,
                   id_str: String,
                   profile_image_url: String)

// Define the tweet class fields
case class ApiCall(action:String,
                   count:BigInt,
                   ip_address:String,
                   action_time: UUID,
                   lat: Double,
                   lon: Double
                   city: String,
                   region: String,
                   country: String)
