/********************************************************************************
* Copyright Hinduja Leyland Finance Limited (HLF) 17-Feb-2025
*
* Licensed under the Hinduja Leyland Finance Limited (HLF), Version 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* Author : @author dhilipkumar ananth
********************************************************************************/
package com.crypto.dto;

import java.io.Serializable;
import java.util.List;

import com.crypto.annotations.Decrypt;
import com.crypto.annotations.Encrypt;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * @author dhilipkumar ananth
 * @created_on 17-Feb-2025
 */
@Data
@JsonInclude(value = Include.NON_NULL)
public class BaseDTO implements Serializable {

    @Encrypt
    private String statusCode;

    @Decrypt
    private String message;
    private String status;
    private String executionTime;
    private Object responseContent;
    private List<?> responseContents;

    public BaseDTO(String statusCode, String message, String status) {
        this.statusCode = statusCode;
        this.message = message;
        this.status = status;
    }

    public BaseDTO() {
    }
}