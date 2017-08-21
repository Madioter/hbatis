/**
 *    Copyright 2009-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.madiot.hbatis.builder.xml;

import com.madiot.hbatis.builder.BaseBuilder;
import com.madiot.hbatis.builder.MapperBuilderAssistant;
import com.madiot.hbatis.mapping.ExecuteCommandType;
import com.madiot.hbatis.mapping.structure.BoundStatement;
import com.madiot.hbatis.parsing.XNode;
import com.madiot.hbatis.session.Configuration;

import java.util.Locale;

/**
 * @author Clinton Begin
 */
public class XMLStatementBuilder extends BaseBuilder {

    private final MapperBuilderAssistant builderAssistant;
    private final XNode context;

    public XMLStatementBuilder(Configuration configuration, MapperBuilderAssistant builderAssistant, XNode context) {
        super(configuration);
        this.builderAssistant = builderAssistant;
        this.context = context;
    }

    public void parseStatementNode() {
        String id = context.getStringAttribute("id");

        Integer fetchSize = context.getIntAttribute("fetchSize");
        Integer timeout = context.getIntAttribute("timeout");
        String parameterMap = context.getStringAttribute("parameterMap");
        String parameterType = context.getStringAttribute("parameterType");
        Class<?> parameterTypeClass = resolveClass(parameterType);
        String resultMap = context.getStringAttribute("resultMap");
        String resultType = context.getStringAttribute("resultType");
        String tableName = context.getStringAttribute("tableName");

        Class<?> resultTypeClass = resolveClass(resultType);

        String nodeName = context.getNode().getNodeName();
        ExecuteCommandType executeCommandType = ExecuteCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
        boolean isSelect = executeCommandType == ExecuteCommandType.SELECT;
        boolean flushCache = context.getBooleanAttribute("flushCache", !isSelect);
        boolean useCache = context.getBooleanAttribute("useCache", isSelect);
        boolean resultOrdered = context.getBooleanAttribute("resultOrdered", false);

        String resultSets = context.getStringAttribute("resultSets");
        String keyProperty = context.getStringAttribute("keyProperty");
        String keyColumn = context.getStringAttribute("keyColumn");

        BoundStatement boundStatement = BoundStatement.newInstance(executeCommandType, context, configuration);

        builderAssistant.addMappedStatement(id, executeCommandType,
                fetchSize, timeout, parameterMap, parameterTypeClass, resultMap, resultTypeClass,
                flushCache, useCache, resultOrdered, keyProperty, keyColumn, resultSets, tableName, boundStatement);
    }
}
