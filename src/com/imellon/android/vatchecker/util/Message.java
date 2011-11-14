/* 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.imellon.android.vatchecker.util;

/**
 * The Class Message.
 * 
 * @author Christos Papazafeiropoulos - Email: christos@imellon.com
 * @author Dimitris Makris - Email: dmakris@imellon.com
 */
public class Message {

	/**
	 * The Enum ERROR_CODES.
	 */
	public static enum ERROR_CODES {

		/** The R g_ no t_ individua l_ nf. */
		RG_NOT_INDIVIDUAL_NF,

		/** The R g_ wron g_ afm. */
		RG_WRONG_AFM,

		/** The VALI d_ afm. */
		VALID_AFM,

		/** The UNKNOW n_ error. */
		UNKNOWN_ERROR
	}

	/** The error code. */
	public ERROR_CODES errorCode = ERROR_CODES.VALID_AFM;

	/** The error descr. */
	public String errorDescr;

	/** The activity_description. */
	public String activity_description;

	/** The zip_code. */
	public String zip_code;

	/** The activity. */
	public String activity;

	/** The start_date. */
	public String start_date;

	/** The end_date. */
	public String end_date;

	/** The tax_office_description. */
	public String tax_office_description;

	/** The area. */
	public String area;

	/** The vat_indication. */
	public String vat_indication;

	/** The address_no. */
	public String address_no;

	/** The address. */
	public String address;

	/** The tax_office_code. */
	public String tax_office_code;

	/** The telephone. */
	public String telephone;

	/** The name. */
	public String name;

	/** The fax. */
	public String fax;

	/** The vat. */
	public String vat;

	/** The title. */
	public String title;
}
