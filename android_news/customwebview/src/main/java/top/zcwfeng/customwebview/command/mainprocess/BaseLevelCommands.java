package top.zcwfeng.customwebview.command.mainprocess;


import top.zcwfeng.customwebview.command.base.Commands;
import top.zcwfeng.customwebview.utils.WebConstants;

public class BaseLevelCommands extends Commands {

    public BaseLevelCommands() {
    }

    @Override
    protected int getCommandLevel() {
        return WebConstants.LEVEL_BASE;
    }
}
