Collection of small platform-agnostic Kotlin utilities.

## [karamel-utils-core](karamel-utils-core)

```kotlin
implementation("io.github.adokky:karamel-utils-core:0.1")
```

Collection of small platform-agnostic Kotlin utilities.

* The module designed to be as compact as possible.
  So much that can be included in almost every app/library as stdlib companion.
* The utilities it provides are platform-agnostic and general-purpose.
I tried to keep only those that proven to be useful in every domain.

## [karamel-utils-ts-bits](karamel-utils-ts-bits)

```kotlin
implementation("io.github.adokky:karamel-utils-ts-bits:0.1")
```

Type-safe bit schema declaration.

Example:

```kotlin
object AnimalTraits: BitDescriptors() {
    val HasLegs  = uniqueBit(5)
    val Has2Eyes = uniqueBit(6)
    val Venomous = uniqueBit(7)
    
    val Snake = Has2Eyes + Venomous
    val Dog = Has2Eyes + HasLegs
}

fun printTraits(traits: BitArray32<AnimalTraits>) {
    if (traits[AnimalTraits.HasLegs])
        println("the animal has legs!")
    if (AnimalTraits.Venomous in traits)
        println("the animal is venomous!")
}
```
