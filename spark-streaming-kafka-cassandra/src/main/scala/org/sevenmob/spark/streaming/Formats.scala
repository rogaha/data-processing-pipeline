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
