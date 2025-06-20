package io.kodec.struct

import karamel.utils.Bits32
import dev.dokky.bitvector.BitVector
import dev.dokky.bitvector.MutableBitVector

/**
 * Base class for type-safe bit schema declaration.
 *
 * Example:
 *
 *      object AnimalTraits: BitDescriptors() {
 *          val HasLegs  = uniqueBit(5)
 *          val Has2Eyes = uniqueBit(6)
 *          val Venomous = uniqueBit(7)
 *
 *          val Snake = Has2Eyes + Venomous
 *          val Dog = Has2Eyes + HasLegs
 *      }
 *
 *      fun printTraits(traits: BitArray32<AnimalTraits>) {
 *          if (traits[AnimalTraits.HasLegs])
 *              println("the animal has legs!")
 *          if (AnimalTraits.Venomous in traits)
 *              println("the animal is venomous!")
 *      }
 */
abstract class BitDescriptors(private val capacity: Int = 32) {
    private val all = MutableBitVector()
    fun getAll(): BitVector = all
    fun getCapacity(): Int = capacity

    protected fun <T: BitDescriptors> T.uniqueBit(index: Int): Bits32<T> {
        require(index < capacity) { "bit index = $index > maximum index = ${capacity-1}" }
        return Bits32<T>(1 shl index).also {
            if (all.put(index)) throw IllegalArgumentException("bit index=$index already set")
        }
    }
}

/**
 * Base class for type-safe bit schema declaration with automatic bit index assignment.
 *
 * Example:
 *
 *      object AnimalTraits: AutoBitDescriptors() {
 *          val HasLegs  = uniqueBit() // bit index is 0
 *          val Has2Eyes = uniqueBit(3)
 *          val Venomous = uniqueBit() // bit index is 4
 *
 *          val Snake = Has2Eyes + Venomous
 *          val Dog = Has2Eyes + HasLegs
 *      }
 *
 *      fun printTraits(traits: BitArray32<AnimalTraits>) {
 *          if (traits[AnimalTraits.HasLegs])
 *              println("the animal has legs!")
 *          if (AnimalTraits.Venomous in traits)
 *              println("the animal is venomous!")
 *      }
 *
 * @see BitDescriptors
 */
abstract class AutoBitDescriptors(capacity: Int = 32): BitDescriptors(capacity) {
    private fun autoIndex(): Int = getAll().last() + 1

    protected fun <T: BitDescriptors> T.uniqueBit(): Bits32<T> = uniqueBit(autoIndex())
}