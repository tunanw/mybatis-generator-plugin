/*
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.itfsw.mybatis.generator.plugins.utils;

import com.itfsw.mybatis.generator.plugins.utils.hook.HookAggregator;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PluginConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * ---------------------------------------------------------------------------
 * 插件工具集
 * ---------------------------------------------------------------------------
 * @author: hewei
 * @time:2017/4/20 15:43
 * ---------------------------------------------------------------------------
 */
public class PluginTools {
    private static final Logger logger = LoggerFactory.getLogger(PluginTools.class);

    /**
     * 获取挂载
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getHook(Class<T> clazz) {
        return (T) HookAggregator.getInstance();
    }

    /**
     * 检查插件依赖
     * @param context 上下文
     * @param plugins 插件
     * @return
     */
    public static boolean checkDependencyPlugin(Context context, Class... plugins) {
        for (Class plugin : plugins) {
            if (getPluginIndex(context, plugin) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取插件所在位置
     * @param context 上下文
     * @param plugin  插件
     * @return -1:未找到
     */
    public static int getPluginIndex(Context context, Class plugin) {
        List<PluginConfiguration> list = getConfigPlugins(context);
        // 检查是否配置了ModelColumnPlugin插件
        for (int i = 0; i < list.size(); i++) {
            PluginConfiguration config = list.get(i);
            if (plugin.getName().equals(config.getConfigurationType())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取插件列表
     * @param ctx 上下文
     * @return
     */
    public static List<PluginConfiguration> getConfigPlugins(Context ctx) {
        try {
            // 利用反射获取pluginConfigurations属性
            return (List<PluginConfiguration>) BeanUtils.getProperty(ctx, "pluginConfigurations");
        } catch (Exception e) {
            logger.error("插件检查反射异常", e);
        }
        return new ArrayList<>();
    }

    /**
     * 获取插件配置
     * @param context 上下文
     * @param plugin  插件
     * @return
     */
    public static PluginConfiguration getPluginConfiguration(Context context, Class plugin) {
        int index = getPluginIndex(context, plugin);
        if (index > -1) {
            return getConfigPlugins(context).get(index);
        }
        return null;
    }
}
