package com.bindglam.neko.compatibility.miniplaceholders

import io.github.miniplaceholders.api.Expansion

interface ExpansionCreator {
    fun create(builder: Expansion.Builder)
}