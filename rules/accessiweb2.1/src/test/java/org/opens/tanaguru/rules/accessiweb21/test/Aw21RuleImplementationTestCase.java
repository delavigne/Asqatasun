/*
 * Tanaguru - Automated webpage assessment
 * Copyright (C) 2008-2012  Open-S Company
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact us by mail: open-s AT open-s DOT com
 */
package org.opens.tanaguru.rules.accessiweb21.test;

import org.opens.tanaguru.rules.test.AbstractRuleImplementationTestCase;

/**
 *
 * @author jkowalczyk
 */
public abstract class Aw21RuleImplementationTestCase extends AbstractRuleImplementationTestCase {

    private static final String TESTCASE_FILE_PATH = "../accessiweb2.1-testcases/src/main/resources/testcases/";
    private static final String INPUT_FILE_DATA_NAME = "../accessiweb2.1-testcases/src/main/resources/dataSets/nomenclatureFlatXmlDataSet.xml";
    private static final String REFERENTIAL = "AW22";

    public Aw21RuleImplementationTestCase(String testName) {
        super(testName, INPUT_FILE_DATA_NAME, TESTCASE_FILE_PATH, REFERENTIAL);
    }
    
    public Aw21RuleImplementationTestCase(String testName, String inputFileDataName) {
        super(testName, inputFileDataName, TESTCASE_FILE_PATH, REFERENTIAL);
    }

}