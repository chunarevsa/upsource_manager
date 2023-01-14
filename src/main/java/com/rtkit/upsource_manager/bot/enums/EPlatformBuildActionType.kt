package com.rtkit.upsource_manager.bot.enums

import java.awt.Color

/** @author Johnson on 05.04.2021*/
enum class EPlatformBuildActionType(
    val isFinal: Boolean,
    val statusOrder: Int,
    val partMessage: String,
    val reaction: EReactionType,
    val color: Color,
    val timeout: Int?
) {
    None(false, -1, "", EReactionType.FAIL, Color.WHITE, null),
    BuildStart(false, 0, "собирается...", EReactionType.START, Color.GRAY, 30 * 60 * 1000),
    BuildSuccess(false, 1, "выкладывается...", EReactionType.START, Color.GRAY, 60_000),
    SonarFailed(true, 1, "**ЗАБРАКОВАНА СОНАРОМ!**", EReactionType.FAIL, Color.RED, 30 * 60 * 1000),
    BuildFailed(true, 1, "**НЕ БЫЛА СОБРАНА!**", EReactionType.FAIL, Color.RED, 30 * 60 * 1000),
    DeployStarted(false, 2, "выкладывается...", EReactionType.START, Color.GRAY, 30 * 60 * 1000),
    DeploySuccess(true, 3, "успешно собрана и выложена!", EReactionType.SUCCESS, Color.GREEN, 60_000),
    DeployCanceled(true, 3, "- _сборка отменена!_", EReactionType.FAIL, Color.RED, 30 * 60 * 1000),
    DeployFialed(true, 3, "**НЕ БЫЛА ВЫЛОЖЕНА!**", EReactionType.FAIL, Color.RED, 30 * 60 * 1000),
    TimedOut(true, 4, "- **НЕ ДОЖДАЛИСЬ СБОРКИ!**", EReactionType.FAIL, Color.RED, null);
}