package karamel.utils

import kotlin.reflect.KClass

internal actual fun KClass<*>.platformQualifiedName(): String? = qualifiedName