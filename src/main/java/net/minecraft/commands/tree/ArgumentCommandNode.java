package net.minecraft.commands.tree;

import net.minecraft.commands.arguments.CommandArgumentType;
import net.minecraft.commands.exceptions.IllegalCommandArgumentException;

public class ArgumentCommandNode<T> extends CommandNode {
    private final String name;
    private final CommandArgumentType<T> type;

    public ArgumentCommandNode(String name, CommandArgumentType<T> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public CommandArgumentType<T> getType() {
        return type;
    }

    @Override
    public CommandNode parse(String command) throws IllegalCommandArgumentException {
        CommandArgumentType.CommandArgumentParseResult<T> parsed = type.parse(command);
        int start = parsed.getRaw().length() + 1;

        if (start < command.length()) {
            String result = command.substring(start);

            for (CommandNode node : getChildren()) {
                try {
                    return node.parse(result);
                } catch (IllegalCommandArgumentException ignored) {
                }
            }

            throw new IllegalCommandArgumentException();
        } else {
            return this;
        }
    }
}