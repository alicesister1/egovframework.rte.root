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
import java.util.Map.Entry;

import javax.jws.WebParam.Mode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import egovframework.rte.itl.integration.type.RecordType;
import egovframework.rte.itl.integration.type.Type;
import egovframework.rte.itl.webservice.EgovWebServiceMessageHeader;
import egovframework.rte.itl.webservice.data.WebServiceServerDefinition;
import egovframework.rte.itl.webservice.service.ServiceEndpointInfo;
import egovframework.rte.itl.webservice.service.ServiceParamInfo;

/**
 * 웹 서비스 ServiceEndpoint 정보 구현 클래스
 * <p>
 * <b>NOTE:</b> 웹 서비스 ServiceEndpoint 정보를 나타내는 class이다.
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
public class ServiceEndpointInfoImpl implements ServiceEndpointInfo {
    private Log LOG = LogFactory.getLog(this.getClass());

    /** namespace */
    private String namespace;

    /** address */
    private String address;

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
     * @param address
     *        address
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
    public ServiceEndpointInfoImpl(String namespace, String address,
            String serviceName, String portName, String operationName,
            ServiceParamInfo returnInfo, Collection<ServiceParamInfo> paramInfos) {
        super();
        if (StringUtils.hasText(namespace) == false) {
            LOG.error("Argument 'namespace' has no text (" + namespace + ")");
            throw new IllegalArgumentException();
        } else if (StringUtils.hasText(address) == false) {
            LOG.error("Argument 'address' has no text (" + address + ")");
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
        this.address = address;
        this.serviceName = serviceName;
        this.portName = portName;
        this.operationName = operationName;
        this.returnInfo = returnInfo;
        this.paramInfos = paramInfos;
    }

    /**
     * Constructor
     * @param webServiceServerDefinition
     *        WebServiceServerDefinition
     * @param requestType
     *        Request Message RecordType
     * @param responseType
     *        Response Message RecordType
     * @throws IllegalArgumentException
     *         Argument 값이 <code>null</code>인 경우
     */
    public ServiceEndpointInfoImpl(
            final WebServiceServerDefinition webServiceServerDefinition,
            final RecordType requestType, final RecordType responseType) {
        super();
        if (webServiceServerDefinition == null) {
            LOG.error("Argument 'webServiceServerDefinition' is null");
            throw new IllegalArgumentException();
        } else if (webServiceServerDefinition.isValid() == false) {
            LOG.error("Argument 'webServiceServerDefinition' is invalid");
            throw new IllegalArgumentException();
        }
        // else if (StringUtils.hasText(
        // webServiceServerDefinition.getNamespace())
        // == false)
        // {
        // LOG.error("Argument 'webServiceServerDefinition.namespace' has "
        // +
        // "no text (" +
        // webServiceServerDefinition.getNamespace() +
        // ")");
        // throw new IllegalArgumentException();
        // }
        // else if (StringUtils.hasText(
        // webServiceServerDefinition.getAddress()) ==
        // false)
        // {
        // LOG.error("Argument 'webServiceServerDefinition.address' has "
        // +
        // "no text (" +
        // webServiceServerDefinition.getAddress() +
        // ")");
        // throw new IllegalArgumentException();
        // }
        // else if (StringUtils.hasText(
        // webServiceServerDefinition.getServiceName())
        // == false)
        // {
        // LOG.error("Argument 'webServiceServerDefinition.serviceName' has "
        // +
        // "no text (" +
        // webServiceServerDefinition.getServiceName()
        // +
        // ")");
        // throw new IllegalArgumentException();
        // }
        // else if (StringUtils.hasText(
        // webServiceServerDefinition.getPortName()) ==
        // false)
        // {
        // LOG.error("Argument 'webServiceServerDefinition.portName' has "
        // +
        // "no text (" +
        // webServiceServerDefinition.getPortName() +
        // ")");
        // throw new IllegalArgumentException();
        // }
        // else if (StringUtils.hasText(
        // webServiceServerDefinition.getOperationName())
        // == false)
        // {
        // LOG.error("Argument 'webServiceServerDefinition.operationName' "
        // +
        // "has no text (" +
        // webServiceServerDefinition.getOperationName()
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

        this.namespace = webServiceServerDefinition.getNamespace();
        this.address = webServiceServerDefinition.getAddress();
        this.serviceName = webServiceServerDefinition.getServiceName();
        this.portName = webServiceServerDefinition.getPortName();
        this.operationName = webServiceServerDefinition.getOperationName();
        this.returnInfo = null;
        this.paramInfos = new ArrayList<ServiceParamInfo>();

        // header
        this.paramInfos.add(new ServiceParamInfoImpl("header",
            EgovWebServiceMessageHeader.TYPE, Mode.INOUT, true));

        // request body
        for (Entry<String, Type> entry : requestType.getFieldTypes().entrySet()) {
            this.paramInfos.add(new ServiceParamInfoImpl(entry.getKey(), entry
                .getValue(), Mode.IN, false));
        }

        // response body
        for (Entry<String, Type> entry : responseType.getFieldTypes()
            .entrySet()) {
            this.paramInfos.add(new ServiceParamInfoImpl(entry.getKey(), entry
                .getValue(), Mode.OUT, false));
        }
    }

    /**
     * @return the namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
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

    public String getWsdlAddress() {
        return null;
    }
}
