package org.sevenmob.spark.streaming

object SampleTwitterData {
val jsonStr = """ {
  "lat": 0.0,
  "lon": 0.0,
  "retweeted_status": {
    "created_at": "Sun Jul 19 02:04:44 +0000 2015",
    "id": 622587848269520896,
    "id_str": "622587848269520896",
    "text": "Awesome contributions! Thanks everyone. https:\/\/t.co\/t7ygZUl1lS",
    "source": "\u003ca href=\"http:\/\/twitter.com\" rel=\"nofollow\"\u003eTwitter Web Client\u003c\/a\u003e",
    "truncated": false,
    "in_reply_to_status_id": null,
    "in_reply_to_status_id_str": null,
    "in_reply_to_user_id": null,
    "in_reply_to_user_id_str": null,
    "in_reply_to_screen_name": null,
    "user": {
      "id": 776322822,
      "id_str": "776322822",
      "name": "Christian Smith",
      "screen_name": "anvilhacks",
      "location": "pacific northwest",
      "url": "http:\/\/anvil.io",
      "description": "Founder of @AnvilResearch, hacker, musician, nature boy, contrarian. Cohost @readthesource + @TheWebPlatform. #OpenID #OAuth #IoT #golang #nodejs",
      "protected": false,
      "verified": false,
      "followers_count": 256,
      "friends_count": 316,
      "listed_count": 34,
      "favourites_count": 464,
      "statuses_count": 913,
      "created_at": "Thu Aug 23 16:43:11 +0000 2012",
      "utc_offset": -25200,
      "time_zone": "Arizona",
      "geo_enabled": false,
      "lang": "en",
      "contributors_enabled": false,
      "is_translator": false,
      "profile_background_color": "C0DEED",
      "profile_background_image_url": "http:\/\/abs.twimg.com\/images\/themes\/theme1\/bg.png",
      "profile_background_image_url_https": "https:\/\/abs.twimg.com\/images\/themes\/theme1\/bg.png",
      "profile_background_tile": false,
      "profile_link_color": "0084B4",
      "profile_sidebar_border_color": "C0DEED",
      "profile_sidebar_fill_color": "DDEEF6",
      "profile_text_color": "333333",
      "profile_use_background_image": true,
      "profile_image_url": "http:\/\/pbs.twimg.com\/profile_images\/608392782554775552\/DPzmgNXJ_normal.jpg",
      "profile_image_url_https": "https:\/\/pbs.twimg.com\/profile_images\/608392782554775552\/DPzmgNXJ_normal.jpg",
      "profile_banner_url": "https:\/\/pbs.twimg.com\/profile_banners\/776322822\/1401989083",
      "default_profile": true,
      "default_profile_image": false,
      "following": null,
      "follow_request_sent": null,
      "notifications": null
    },
    "geo": null,
    "coordinates": null,
    "place": null,
    "contributors": null,
    "quoted_status_id": 622587083497033728,
    "quoted_status_id_str": "622587083497033728",
    "quoted_status": {
      "created_at": "Sun Jul 19 02:01:42 +0000 2015",
      "id": 622587083497033728,
      "id_str": "622587083497033728",
      "text": "Weekend #hackathons w #opensource team kick ass! Thx @VartanSimonian @oreng @adi_ads #nodejs #docker #anvilconnect http:\/\/t.co\/v8wDozwqil",
      "source": "\u003ca href=\"http:\/\/www.hootsuite.com\" rel=\"nofollow\"\u003eHootsuite\u003c\/a\u003e",
      "truncated": false,
      "in_reply_to_status_id": null,
      "in_reply_to_status_id_str": null,
      "in_reply_to_user_id": null,
      "in_reply_to_user_id_str": null,
      "in_reply_to_screen_name": null,
      "user": {
        "id": 3161807689,
        "id_str": "3161807689",
        "name": "Anvil Research, Inc.",
        "screen_name": "AnvilResearch",
        "location": "",
        "url": "http:\/\/anvil.io",
        "description": "We're building the definitive identity and access hub. It's the only thing you'll need to connect everything. And it's completely open source.",
        "protected": false,
        "verified": false,
        "followers_count": 44,
        "friends_count": 179,
        "listed_count": 12,
        "favourites_count": 10,
        "statuses_count": 28,
        "created_at": "Sat Apr 18 02:04:50 +0000 2015",
        "utc_offset": -25200,
        "time_zone": "Pacific Time (US & Canada)",
        "geo_enabled": false,
        "lang": "en",
        "contributors_enabled": false,
        "is_translator": false,
        "profile_background_color": "C0DEED",
        "profile_background_image_url": "http:\/\/abs.twimg.com\/images\/themes\/theme1\/bg.png",
        "profile_background_image_url_https": "https:\/\/abs.twimg.com\/images\/themes\/theme1\/bg.png",
        "profile_background_tile": false,
        "profile_link_color": "0084B4",
        "profile_sidebar_border_color": "C0DEED",
        "profile_sidebar_fill_color": "DDEEF6",
        "profile_text_color": "333333",
        "profile_use_background_image": true,
        "profile_image_url": "http:\/\/pbs.twimg.com\/profile_images\/618931858579939329\/PEloKln1_normal.png",
        "profile_image_url_https": "https:\/\/pbs.twimg.com\/profile_images\/618931858579939329\/PEloKln1_normal.png",
        "profile_banner_url": "https:\/\/pbs.twimg.com\/profile_banners\/3161807689\/1436400902",
        "default_profile": true,
        "default_profile_image": false,
        "following": null,
        "follow_request_sent": null,
        "notifications": null
      },
      "geo": null,
      "coordinates": null,
      "place": null,
      "contributors": null,
      "retweet_count": 0,
      "favorite_count": 0,
      "entities": {
        "hashtags": [
          {
            "text": "hackathons",
            "indices": [
              8,
              19
            ]
          },
          {
            "text": "opensource",
            "indices": [
              22,
              33
            ]
          },
          {
            "text": "nodejs",
            "indices": [
              85,
              92
            ]
          },
          {
            "text": "docker",
            "indices": [
              93,
              100
            ]
          },
          {
            "text": "anvilconnect",
            "indices": [
              101,
              114
            ]
          }
        ],
        "trends": [
          
        ],
        "urls": [
          {
            "url": "http:\/\/t.co\/v8wDozwqil",
            "expanded_url": "http:\/\/bit.ly\/1Thxhl3",
            "display_url": "bit.ly\/1Thxhl3",
            "indices": [
              115,
              137
            ]
          }
        ],
        "user_mentions": [
          {
            "screen_name": "VartanSimonian",
            "name": "Vartan Simonian",
            "id": 12768142,
            "id_str": "12768142",
            "indices": [
              53,
              68
            ]
          },
          {
            "screen_name": "oreng",
            "name": "oreng",
            "id": 8399312,
            "id_str": "8399312",
            "indices": [
              69,
              75
            ]
          },
          {
            "screen_name": "adi_ads",
            "name": "Adi",
            "id": 51087762,
            "id_str": "51087762",
            "indices": [
              76,
              84
            ]
          }
        ],
        "symbols": [
          
        ]
      },
      "favorited": false,
      "retweeted": false,
      "possibly_sensitive": false,
      "filter_level": "low",
      "lang": "en"
    },
    "retweet_count": 2,
    "favorite_count": 0,
    "entities": {
      "hashtags": [
        
      ],
      "trends": [
        
      ],
      "urls": [
        {
          "url": "https:\/\/t.co\/t7ygZUl1lS",
          "expanded_url": "https:\/\/twitter.com\/AnvilResearch\/status\/622587083497033728",
          "display_url": "twitter.com\/AnvilResearch\/\u2026",
          "indices": [
            40,
            63
          ]
        }
      ],
      "user_mentions": [
        
      ],
      "symbols": [
        
      ]
    },
    "favorited": false,
    "retweeted": false,
    "possibly_sensitive": false,
    "filter_level": "low",
    "lang": "en"
  }
}
"""
}

