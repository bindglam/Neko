package com.bindglam.neko.content.item.block.mechanism

import com.bindglam.neko.api.content.item.block.CustomBlock
import com.bindglam.neko.api.content.MechanismFactory

class NoteBlockMechanismFactory : MechanismFactory<CustomBlock> {
    override fun create(block: CustomBlock): NoteBlockMechanism = NoteBlockMechanism(block)

    override fun type(): Class<CustomBlock> = CustomBlock::class.java
}