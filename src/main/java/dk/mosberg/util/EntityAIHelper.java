package dk.mosberg.util;

import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;

/**
 * Utilities for managing AI goals on {@link MobEntity} instances. All methods are server-side and
 * mutate the entity's goal/target selectors in place.
 */
public final class EntityAIHelper {
    private static final Field GOAL_SELECTOR_FIELD = getSelectorField("goalSelector");
    private static final Field TARGET_SELECTOR_FIELD = getSelectorField("targetSelector");

    private EntityAIHelper() {}

    /** Adds a goal to the mob's primary selector. */
    public static void addGoal(@NotNull MobEntity mob, int priority, @NotNull Goal goal) {
        selector(GOAL_SELECTOR_FIELD, mob).add(priority, goal);
    }

    /** Adds a goal to the mob's target selector. */
    public static void addTargetGoal(@NotNull MobEntity mob, int priority, @NotNull Goal goal) {
        selector(TARGET_SELECTOR_FIELD, mob).add(priority, goal);
    }

    /** Removes a goal from the mob's primary selector. */
    public static void removeGoal(@NotNull MobEntity mob, @NotNull Goal goal) {
        selector(GOAL_SELECTOR_FIELD, mob).remove(goal);
    }

    /** Removes a goal from the mob's target selector. */
    public static void removeTargetGoal(@NotNull MobEntity mob, @NotNull Goal goal) {
        selector(TARGET_SELECTOR_FIELD, mob).remove(goal);
    }

    /** Clears all primary goals from the mob. */
    public static void clearGoals(@NotNull MobEntity mob) {
        selector(GOAL_SELECTOR_FIELD, mob).clear(g -> true);
    }

    /** Clears all target goals from the mob. */
    public static void clearTargetGoals(@NotNull MobEntity mob) {
        selector(TARGET_SELECTOR_FIELD, mob).clear(g -> true);
    }

    private static GoalSelector selector(Field field, MobEntity mob) {
        try {
            return (GoalSelector) field.get(mob);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Unable to access goal selector", e);
        }
    }

    private static Field getSelectorField(String name) {
        try {
            var field = MobEntity.class.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException("Missing selector field: " + name, e);
        }
    }
}
