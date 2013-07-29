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
package egovframework.rte.itl.integration.metadata.dao;

import egovframework.rte.itl.integration.metadata.ServiceDefinition;

/**
 * 연계 서비스의 서비스를 정의하기 위한 ServiceDefinition 메타정보를 읽어오기 위한
 * DAO 인터페이스
 * <p>
 * <b>NOTE:</b> 전자정부 연계 서비스의 서비스를 정의하기 위한
 * ServiceDefinition 메타정보를 읽어오기 위한 DAO Interface이다.
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
public interface ServiceDefinitionDao {

    /**
     * <code>key</code>를 Primary key로 갖는
     * ServiceDefinition을 읽어온다.
     * @param key
     *        key
     * @return ServiceDefinition 객체. 만약 존재하지 않을 경우
     *         <code>null</code>
     */
    public ServiceDefinition getServiceDefinition(final String key);

    /**
     * <code>systemKey</code>, <code>serviceId</code>를
     * alternative key로 갖는 ServiceDefinition을 읽어온다.
     * @param systemKey
     *        시스템 Key
     * @param serviceId
     *        서비스 ID
     * @return ServiceDefinition 객체. 만약 존재하지 않을 경우
     *         <code>null</code>
     */
    public ServiceDefinition getServiceDefinition(final String systemKey,
            final String serviceId);

    /**
     * <code>organzationId</code>,
     * <code>systemId</code>, <code>serviceId</code>를
     * alternative key로 갖는 ServiceDefinition을 읽어온다.
     * @param organizationId
     *        기관 ID
     * @param systemId
     *        시스템 ID
     * @param serviceId
     *        서비스 ID
     * @return ServiceDefinition 객체. 만약 존재하지 않을 경우
     *         <code>null</code>
     */
    public ServiceDefinition getServiceDefinition(final String organizationId,
            final String systemId, final String serviceId);

}
