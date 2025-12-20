package com.bindglam.neko.api.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class BlockBreakStateEvent extends BlockEvent {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;
    private final State state;

    @ApiStatus.Internal
    public BlockBreakStateEvent(@NotNull Block block, Player player, State state) {
        super(block);
        this.player = player;
        this.state = state;
    }

    public Player getPlayer() {
        return player;
    }

    public State getState() {
        return state;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public enum State {
        START,
        END
    }
}
