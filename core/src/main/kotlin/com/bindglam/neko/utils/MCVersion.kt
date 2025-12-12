package com.bindglam.neko.utils

data class MCVersion(private val major: Int, private val minor: Int, private val patch: Int) {
    companion object {
        val v1_21_11 = MCVersion(1, 21, 11)
        val v1_21_10 = MCVersion(1, 21, 10)
        val v1_21_9 = MCVersion(1, 21, 9)
        val v1_21_8 = MCVersion(1, 21, 8)
        val v1_21_4 = MCVersion(1, 21, 4)

        fun parse(version: String): MCVersion {
            version.split('.').also {
                return if(it.size >= 3)
                    MCVersion(it[0].toInt(), it[1].toInt(), it[2].toInt())
                else
                    MCVersion(it[0].toInt(), it[1].toInt(), 0)
            }
        }
    }

    override fun toString(): String {
        return if(patch == 0) "${major}.${minor}" else "${major}.${minor}.${patch}"
    }
}