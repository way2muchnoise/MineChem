package minechem.command;

import minechem.handler.*;
import minechem.registry.RecipeBreakdownsRegistry;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ReloadCommand implements ISubCommand {

    public static final String[] reloadables = { "fuels", "reactions", "pages", "breakdowns", "printing", "acids" };

    @Override
    public String getCommandName() {
        return "reload";
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] arguments) throws CommandException {
        if (arguments.length == 1) {
            String arg = arguments[0];
            String reply = null;
            if (reloadables[0].startsWith(arg)) {
                FuelHandler.reload();
                reply = "command.minechem.reload.fuels";
            } else if (reloadables[1].startsWith(arg)) {
                RecipeBreakdownsRegistry.getInstance().fullClear();
                ReactionHandler.reload();
                reply = "command.minechem.reload.reactions";
            } else if (reloadables[2].startsWith(arg)) {
                StructuredJournalHandler.reload();
                reply = "command.minechem.reload.pages";
            } else if (reloadables[3].startsWith(arg)) {
                RecipeBreakdownsRegistry.getInstance().clear();
                reply = "command.minechem.reload.breakdowns";
            } else if (reloadables[4].startsWith(arg)) {
                PrintingHandler.reload();
                reply = "command.minechem.reload.printing";
            } else if (reloadables[5].startsWith(arg)) {
                AcidHandler.reload();
                reply = "command.minechem.reload.acids";
            }
            if (reply != null) {
                sender.sendMessage(new TextComponentTranslation(reply));
            }
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            return Arrays.asList(reloadables);
        } else if (args.length == 1) {
            List<String> list = new LinkedList<>();
            for (String reloadable : reloadables) {
                if (reloadable.startsWith(args[0])) {
                    list.add(reloadable);
                }
            }
            return list;
        }
        return new ArrayList<>();
    }
}
