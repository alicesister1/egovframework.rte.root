/*
 * Copyright 2008-2009 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.rte.itl.webservice.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.jws.WebParam.Mode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import egovframework.rte.itl.integration.type.RecordType;
import egovframework.rte.itl.integration.type.Type;
import egovframework.rte.itl.webservice.EgovWebServiceMessageHeader;
import egovframework.rte.itl.webservice.data.MappingInfo;
import egovframework.rte.itl.webservice.data.WebServiceClientDefinition;
import egovframework.rte.itl.webservice.service.ServiceEndpointInterfaceInfo;
import egovframework.rte.itl.webservice.service.ServiceParamInfo;

/**
 * 웹 서비스 ServiceEndpointInterface 정보 구현 클래스
 * <p>
 * <b>NOTE:</b> 웹 서비스 ServiceEndpointInterface 정보를 나타내는
 * class이다.
 * @author 실행환경 개발팀 심상호
 * @since 2009.06.01
 * @version 1.0
 * @see <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.06.01  심상호           최초 생성
 * 
 * </pre>
 */
public class ServiceEndpointInterfaceInfoImpl implements
        ServiceEndpointInterfaceInfo {
    private Log LOG = LogFactory.getLog(this.getClass());

    /** namespace */
    private String namespace;

    /** wsdl address */
    private String wsdlAddress;

    /** service name */
    private String serviceName;

    /** port name */
    private String portName;

    /** operation name */
    private String operationName;

    /** return info */
    private ServiceParamInfo returnInfo;

    /** param info */
    private Collection<ServiceParamInfo> paramInfos;

    /**
     * Constructor
     * @param namespace
     *        namespace
     * @param wsdlAddress
     *        wsdl address
     * @param serviceName
     *        service name
     * @param portName
     *        port name
     * @param operationName
     *        operation name
     * @param returnInfo
     *        return info
     * @param paramInfos
     *        param info
     * @throws IllegalArgumentException
     *         Argument 값이 <code>null</code>인 경우
     */
    public ServiceEndpointInterfaceInfoImpl(String namespace,
            String wsdlAddress, String serviceName, String portName,
            String operationName, ServiceParamInfo returnInfo,
            Collection<ServiceParamInfo> paramInfos) {
        super();
        if (StringUtils.hasText(namespace) == false) {
            LOG.error("Argument 'namespace' has no text (" + namespace + ")");
            throw new IllegalArgumentException();
        } else if (StringUtils.hasText(wsdlAddress) == false) {
            LOG.error("Argument 'wsdlAddress' has no text (" + wsdlAddress
                + ")");
            throw new IllegalArgumentException();
        } else if (StringUtils.hasText(serviceName) == false) {
            LOG.error("Argument 'serviceName' has no text (" + serviceName
                + ")");
            throw new IllegalArgumentException();
        } else if (StringUtils.hasText(portName) == false) {
            LOG.error("Argument 'portName' has no text (" + portName + ")");
            throw new IllegalArgumentException();
        } else if (StringUtils.hasText(operationName) == false) {
            LOG.error("Argument 'operationName' has no text (" + operationName
                + ")");
            throw new IllegalArgumentException();
        } else if (paramInfos == null) {
            LOG.error("Argument 'paramInfos' is null");
            throw new IllegalArgumentException();
        }
        this.namespace = namespace;
        this.wsdlAddress = wsdlAddress;
        this.serviceName = serviceName;
        this.portName = portName;
        this.operationName = operationName;
        this.returnInfo = returnInfo;
        this.paramInfos = paramInfos;
    }

    /**
     * Constructor
     * @param webServiceClientDefinition
     *        WebServiceClientDefinition
     * @param requestType
     *        Request Message RecordType
     * @param responseType
     *        Response Message RecordType
     */
    public ServiceEndpointInterfaceInfoImpl(
            final WebServiceClientDefinition webServiceClientDefinition,
            final RecordType requestType, final RecordType responseType) {
        super();
        if (webServiceClientDefinition == null) {
            LOG.error("Argument 'webServiceClientDefinition' is null");
            throw new IllegalArgumentException();
        } else if (webServiceClientDefinition.isValid() == false) {
            LOG.error("Argument 'webServiceClientDefinition' is invalid");
            throw new IllegalArgumentException();
        }
        // else if (StringUtils.hasText(
        // webServiceClientDefinition.getNamespace())
        // == false)
        // {
        // LOG.error("Argument 'webServiceClientDefinition.namespace' has "
        // +
        // "no text (" +
        // webServiceClientDefinition.getNamespace() +
        // ")");
        // throw new IllegalArgumentException();
        // }
        // else if (StringUtils.hasText(
        // webServiceClientDefinition.getWsdlAddress())
        // == false)
        // {
        // LOG.error("Argument 'webServiceClientDefinition.wsdlAddress' has "
        // +
        // "no text (" +
        // webServiceClientDefinition.getWsdlAddress()
        // +
        // ")");
        // throw new IllegalArgumentException();
        // }
        // else if (StringUtils.hasText(
        // webServiceClientDefinition.getServiceName())
        // == false)
        // {
        // LOG.error("Argument 'webServiceClientDefinition.serviceName' has "
        // +
        // "no text (" +
        // webServiceClientDefinition.getServiceName()
        // +
        // ")");
        // throw new IllegalArgumentException();
        // }
        // else if (StringUtils.hasText(
        // webServiceClientDefinition.getPortName()) ==
        // false)
        // {
        // LOG.error("Argument 'webServiceClientDefinition.portName' has "
        // +
        // "no text (" +
        // webServiceClientDefinition.getPortName() +
        // ")");
        // throw new IllegalArgumentException();
        // }
        // else if (StringUtils.hasText(
        // webServiceClientDefinition.getOperationName())
        // == false)
        // {
        // LOG.error("Argument 'webServiceClientDefinition.operationName' "
        // +
        // "has no text (" +
        // webServiceClientDefinition.getOperationName()
        // + ")");
        // throw new IllegalArgumentException();
        // }
        else if (requestType == null) {
            LOG.error("Argument 'requestType' is null");
            throw new IllegalArgumentException();
        } else if (responseType == null) {
            LOG.error("Argument 'responseType' is null");
            throw new IllegalArgumentException();
        }

        this.namespace = webServiceClientDefinition.getNamespace();
        this.wsdlAddress = webServiceClientDefinition.getWsdlAddress();
        this.serviceName = webServiceClientDefinition.getServiceName();
        this.portName = webServiceClientDefinition.getPortName();
        this.operationName = webServiceClientDefinition.getOperationName();

        if (webServiceClientDefinition.getServiceDefinition().isStandard()) {
            this.paramInfos = new ArrayList<ServiceParamInfo>();

            // header
            this.paramInfos.add(new ServiceParamInfoImpl("header",
                EgovWebServiceMessageHeader.TYPE, Mode.INOUT, true));

            // request body
            for (Entry<String, Type> entry : requestType.getFieldTypes()
                .entrySet()) {
                this.paramInfos.add(new ServiceParamInfoImpl(entry.getKey(),
                    entry.getValue(), Mode.IN, false));
            }

            // response body
            for (Entry<String, Type> entry : responseType.getFieldTypes()
                .entrySet()) {
                this.paramInfos.add(new ServiceParamInfoImpl(entry.getKey(),
                    entry.getValue(), Mode.OUT, false));
            }
        } else {
            Map<String, ServiceParamInfo> paramInfoMap =
                new HashMap<String, ServiceParamInfo>();

            if (webServiceClientDefinition.getRequestMappingInfos() != null) {
                for (Entry<String, MappingInfo> entry : webServiceClientDefinition
                    .getRequestMappingInfos().entrySet()) {
                    String fieldName = entry.getKey();
                    MappingInfo mappingInfo = entry.getValue();
                    Type fieldType = requestType.getFieldType(fieldName);

                    ServiceParamInfoImpl paramInfo =
                        new ServiceParamInfoImpl(fieldName, fieldType, Mode.IN,
                            mappingInfo.isHeader());
                    paramInfoMap.put(fieldName, paramInfo);
                }
            }

            if (webServiceClientDefinition.getResponseMappingInfos() != null) {
                for (Entry<String, MappingInfo> entry : webServiceClientDefinition
                    .getResponseMappingInfos().entrySet()) {
                    String fieldName = entry.getKey();
                    MappingInfo mappingInfo = entry.getValue();
                    Type fieldType = responseType.getFieldType(fieldName);

                    ServiceParamInfoImpl paramInfo =
                        (ServiceParamInfoImpl) paramInfoMap.get(fieldName);
                    if (paramInfo != null) {
                        if (paramInfo.isHeader() == mappingInfo.isHeader()) {
                            if (paramInfo.getType().equals(fieldType) == false) {
                                // TODO : throw
                                // exception
                            }
                            paramInfo.setMode(Mode.INOUT);
                        } else {
                            paramInfo = null;
                        }
                    }
                    if (paramInfo == null) {
                        paramInfo =
                            new ServiceParamInfoImpl(fieldName, fieldType,
                                Mode.OUT, mappingInfo.isHeader());
                        paramInfoMap.put(fieldName, paramInfo);
                    }
                }
            }

            this.returnInfo = null;
            this.paramInfos = paramInfoMap.values();
        }
    }

    /**
     * @return the namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @return the wsdlAddress
     */
    public String getWsdlAddress() {
        return wsdlAddress;
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @return the portName
     */
    public String getPortName() {
        return portName;
    }

    /**
     * @return the operationName
     */
    public String getOperationName() {
        return operationName;
    }

    /**
     * @return the returnInfo
     */
    public ServiceParamInfo getReturnInfo() {
        return returnInfo;
    }

    /**
     * @return the paramInfos
     */
    public Collection<ServiceParamInfo> getParamInfos() {
        return paramInfos;
    }
}
