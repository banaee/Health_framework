/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TSMining_pkg;

/**
 *
 * @author hadi
 */
/*
 002     * Licensed to the Apache Software Foundation (ASF) under one or more
 003     * contributor license agreements.  See the NOTICE file distributed with
 004     * this work for additional information regarding copyright ownership.
 005     * The ASF licenses this file to You under the Apache License, Version 2.0
 006     * (the "License"); you may not use this file except in compliance with
 007     * the License.  You may obtain a copy of the License at
 008     *
 009     *      http://www.apache.org/licenses/LICENSE-2.0
 010     *
 011     * Unless required by applicable law or agreed to in writing, software
 012     * distributed under the License is distributed on an "AS IS" BASIS,
 013     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 014     * See the License for the specific language governing permissions and
 015     * limitations under the License.
 016     */
import org.apache.commons.math.MathException;
import org.apache.commons.math.analysis.UnivariateRealFunction;

/**
 * 023 * Interface representing a univariate real interpolating function. 024 *
 * 025
 *
 * @version $Revision: 821626 $ $Date: 2009-10-04 23:57:30 +0200 (dim. 04 oct.
 * 2009) $ 026
 */
public interface UnivariateRealInterpolator {

    /**
     * 030 * Computes an interpolating function for the data set. 031
     *
     * @param xval the arguments for the interpolation points 032
     * @param yval the values for the interpolation points 033
     * @return a function which interpolates the data set 034
     * @throws MathException if arguments violate assumptions made by the 035 *
     * interpolation algorithm 036
     */
    UnivariateRealFunction interpolate(double xval[], double yval[])
            throws MathException;
}
