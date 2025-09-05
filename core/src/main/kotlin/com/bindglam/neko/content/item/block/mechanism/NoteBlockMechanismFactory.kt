package com.bindglam.neko.content.item.block.mechanism

import com.bindglam.neko.api.content.item.block.CustomBlock
import com.bindglam.neko.api.content.item.block.mechanism.MechanismFactory

class NoteBlockMechanismFactory : MechanismFactory<NoteBlockMechanism> {
    override fun create(block: CustomBlock): NoteBlockMechanism = NoteBlockMechanism(block)
}