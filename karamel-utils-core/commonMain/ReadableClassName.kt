@file:JvmName("KClassNameUtils")

package karamel.utils

import kotlin.jvm.JvmName
import kotlin.reflect.KClass

fun KClass<*>.readableName(): String = platformQualifiedName() ?: simpleName ?: toString()

// KClass<*>.qualifiedName is not supported yet in JavaScript
internal expect fun KClass<*>.platformQualifiedName(): String?