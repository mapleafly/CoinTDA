/*
 * Copyright 2019 xuelf.
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
package org.lifxue.cointda.bean;

/**
 *
 * @author xuelf
 */
public class CoinMarketCapStatus {
    private String timestamp;
    private Integer error_code;
    private String error_message;
    private Integer elapsed;
    private Integer credit_count;
    private String notice;

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the error_code
     */
    public Integer getError_code() {
        return error_code;
    }

    /**
     * @param error_code the error_code to set
     */
    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    /**
     * @return the error_message
     */
    public String getError_message() {
        return error_message;
    }

    /**
     * @param error_message the error_message to set
     */
    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    /**
     * @return the elapsed
     */
    public Integer getElapsed() {
        return elapsed;
    }

    /**
     * @param elapsed the elapsed to set
     */
    public void setElapsed(Integer elapsed) {
        this.elapsed = elapsed;
    }

    /**
     * @return the credit_count
     */
    public Integer getCredit_count() {
        return credit_count;
    }

    /**
     * @param credit_count the credit_count to set
     */
    public void setCredit_count(Integer credit_count) {
        this.credit_count = credit_count;
    }

    /**
     * @return the notice
     */
    public String getNotice() {
        return notice;
    }

    /**
     * @param notice the notice to set
     */
    public void setNotice(String notice) {
        this.notice = notice;
    }
    
}
