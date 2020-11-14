package com.xhstormr.app

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.nio.charset.Charset

object CharsetSerializer : KSerializer<Charset> {

    override val descriptor =
        PrimitiveSerialDescriptor("Charset", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder) =
        charset(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: Charset) =
        encoder.encodeString(value.name())
}
