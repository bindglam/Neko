package com.bindglam.neko.content.item.block.mechanism

import com.bindglam.neko.api.content.item.block.CustomBlock
import com.bindglam.neko.api.content.item.block.mechanism.MechanismFactory

class NoteBlockMechanismFactory : MechanismFactory {
    override fun create(block: CustomBlock): NoteBlockMechanism = NoteBlockMechanism(block)
}