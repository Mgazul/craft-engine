package net.momirealms.craftengine.bukkit.plugin.injector;

import java.lang.reflect.Field;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import net.bytebuddy.implementation.bytecode.member.FieldAccess;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;
import net.bytebuddy.matcher.ElementMatchers;
import net.momirealms.craftengine.bukkit.plugin.reflection.minecraft.NetworkReflections;

import java.lang.reflect.Modifier;

public final class ProtectedFieldVisitor {
    private static FieldAccessor internalFieldAccessor;

    public static void init() throws Exception {
        // Since the field can be directly accessed, we create a simple implementation
        internalFieldAccessor = new FieldAccessor() {
            private final Field entityIdField = NetworkReflections.field$ClientboundMoveEntityPacket$entityId;

            @Override
            public int field$ClientboundMoveEntityPacket$entityId(Object packet) {
                try {
                    return entityIdField.getInt(packet);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to access entityId field", e);
                }
            }
        };
    }

    public static FieldAccessor get() {
        return internalFieldAccessor;
    }
}
