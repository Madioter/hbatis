/**
 *    Copyright 2009-2015 the original author or authors.
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
package com.madiot.hbatis.session;

import com.madiot.hbatis.exceptions.PersistenceException;

/**
 * @author Clinton Begin
 */
public class ExecuteSessionException extends PersistenceException {

  private static final long serialVersionUID = 3833184690240265047L;

  public ExecuteSessionException() {
    super();
  }

  public ExecuteSessionException(String message) {
    super(message);
  }

  public ExecuteSessionException(String message, Throwable cause) {
    super(message, cause);
  }

  public ExecuteSessionException(Throwable cause) {
    super(cause);
  }
}
