package org.sevenmob.spark.streaming

import org.json4s.CustomSerializer
import org.json4s.JsonAST.{JString, JNull}

import java.util.UUID

case object UUIDSerialiser extends CustomSerializer[java.util.UUID](format => (
        {
         case JString(s) => UUID.fromString(s)
         case JNull => null
        },
        {
         case x: UUID => JString(x.toString)
        }
     )
)
