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

import egovframework.rte.itl.integration.metadata.OrganizationDefinition;

/**
 * 연계 서비스 메타 데이터의 OrganizationDefinition 을 읽어오기 위한 DAO
 * 인터페이스
 * <p>
 * <b>NOTE:</b> 전자정부 연계 서비스 메타 데이터의
 * OrganizationDefinition 을 읽어오기 위한 Data Access Object
 * interface이다.
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
public interface OrganizationDefinitionDao {
    /**
     * 기관 primary key (<code>id</code>)로 등록되어 잇는
     * OrganizationDefinition을 읽어온다.
     * @param id
     *        기관 ID
     * @return OrganizationDefinitino, 해당하는 값이 없을 경우
     *         <code>null</code>
     */
    public OrganizationDefinition getOrganizationDefinition(final String id);

}
