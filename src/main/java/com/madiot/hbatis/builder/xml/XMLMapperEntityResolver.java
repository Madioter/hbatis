/**
 *    Copyright 2009-2016 the original author or authors.
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

import com.madiot.hbatis.io.Resources;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Offline entity resolver for the MyBatis DTDs
 *
 * @author Clinton Begin
 * @author Eduardo Macarron
 */
public class XMLMapperEntityResolver implements EntityResolver {

    private static final String HBATIS_CONFIG_SYSTEM = "hbatis-config.dtd";
    private static final String HBATIS_MAPPER_SYSTEM = "hbatis-mapper.dtd";

    private static final String HBATIS_CONFIG_DTD = "com/madiot/hbatis/builder/xml/hbatis-config.dtd";
    private static final String HBATIS_MAPPER_DTD = "com/madiot/hbatis/builder/xml/hbatis-mapper.dtd";

    /*
     * Converts a public DTD into a local one
     *
     * @param publicId The public id that is what comes after "PUBLIC"
     * @param systemId The system id that is what comes after the public id.
     * @return The InputSource for the DTD
     *
     * @throws org.xml.sax.SAXException If anything goes wrong
     */
    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
        try {
            if (systemId != null) {
                String lowerCaseSystemId = systemId.toLowerCase(Locale.ENGLISH);
                if (lowerCaseSystemId.contains(HBATIS_CONFIG_SYSTEM)) {
                    return getInputSource(HBATIS_CONFIG_DTD, publicId, systemId);
                } else if (lowerCaseSystemId.contains(HBATIS_MAPPER_SYSTEM) || lowerCaseSystemId.contains(HBATIS_MAPPER_SYSTEM)) {
                    return getInputSource(HBATIS_MAPPER_DTD, publicId, systemId);
                }
            }
            return null;
        } catch (Exception e) {
            throw new SAXException(e.toString());
        }
    }

    private InputSource getInputSource(String path, String publicId, String systemId) {
        InputSource source = null;
        if (path != null) {
            try {
                InputStream in = Resources.getResourceAsStream(path);
                source = new InputSource(in);
                source.setPublicId(publicId);
                source.setSystemId(systemId);
            } catch (IOException e) {
                // ignore, null is ok
            }
        }
        return source;
    }

}