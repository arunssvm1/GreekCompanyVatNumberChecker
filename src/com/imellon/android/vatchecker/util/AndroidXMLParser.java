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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.imellon.android.vatchecker.util.Message.ERROR_CODES;

import android.util.Log;

/**
 * The Class AndroidXMLParser.
 * 
 * @author Christos Papazafeiropoulos - Email: christos@imellon.com
 * @author Dimitris Makris - Email: dmakris@imellon.com
 */
public class AndroidXMLParser {

	/** The Constant TAG. */
	private static final String TAG = AndroidXMLParser.class.getSimpleName();

	/** The xml. */
	private String xml;

	/**
	 * The Enum XML_TAGS.
	 */
	public enum XML_TAGS {

		/** The error code. */
		errorCode,
		/** The error descr. */
		errorDescr,
		/** The act long descr. */
		actLongDescr,
		/** The postal zip code. */
		postalZipCode,
		/** The fac activity. */
		facActivity,
		/** The regist date. */
		registDate,
		/** The stop date. */
		stopDate,
		/** The doy descr. */
		doyDescr,
		/** The par description. */
		parDescription,
		/** The deactivation flag. */
		deactivationFlag,
		/** The postal address no. */
		postalAddressNo,
		/** The postal address. */
		postalAddress,
		/** The doy. */
		doy,
		/** The firm phone. */
		firmPhone,
		/** The onomasia. */
		onomasia,
		/** The firm fax. */
		firmFax,
		/** The afm. */
		afm,
		/** The commer title. */
		commerTitle
	}

	/** The all. */
	String[] all = { XML_TAGS.actLongDescr.toString(),
			XML_TAGS.postalZipCode.toString(), XML_TAGS.facActivity.toString(),
			XML_TAGS.registDate.toString(), XML_TAGS.stopDate.toString(),
			XML_TAGS.doyDescr.toString(), XML_TAGS.parDescription.toString(),
			XML_TAGS.deactivationFlag.toString(),
			XML_TAGS.postalAddressNo.toString(),
			XML_TAGS.postalAddress.toString(), XML_TAGS.doy.toString(),
			XML_TAGS.firmPhone.toString(), XML_TAGS.onomasia.toString(),
			XML_TAGS.firmFax.toString(), XML_TAGS.afm.toString(),
			XML_TAGS.commerTitle.toString() };

	/**
	 * Instantiates a new android xml parser.
	 * 
	 * @param xml_data
	 *            the xml_data
	 */
	public AndroidXMLParser(String xml_data) {
		xml = xml_data;
	}

	/**
	 * Parses the.
	 * 
	 * @return the message
	 */
	public Message parse() {
		Message m = new Message();

		Pattern regex = Pattern.compile("<ns1:" + XML_TAGS.errorCode.toString()
				+ ">(.*?)</ns1:" + XML_TAGS.errorCode.toString() + ">",
				Pattern.DOTALL);
		Matcher matcher = regex.matcher(xml);
		if (matcher.find()) {
			String DataElements = matcher.group(1);
			Log.d(TAG, XML_TAGS.errorCode.toString() + ": " + DataElements);
			switch (ERROR_CODES.valueOf(DataElements)) {
			case RG_NOT_INDIVIDUAL_NF:
				m.errorCode = ERROR_CODES.RG_NOT_INDIVIDUAL_NF;
				break;
			case RG_WRONG_AFM:
				m.errorCode = ERROR_CODES.RG_WRONG_AFM;
				break;
			default:
				m.errorCode = ERROR_CODES.UNKNOWN_ERROR;
				break;
			}

			Pattern regex2 = Pattern.compile(
					"<ns1:" + XML_TAGS.errorDescr.toString() + ">(.*?)</ns1:"
							+ XML_TAGS.errorDescr.toString() + ">",
					Pattern.DOTALL);
			Matcher matcher2 = regex2.matcher(xml);
			if (matcher2.find()) {
				m.errorDescr = matcher2.group(1);
			}
			return m;
		}

		for (String str : all) {
			Pattern regex2 = Pattern.compile("<ns1:" + str + ">(.*?)</ns1:"
					+ str + ">", Pattern.DOTALL);
			Matcher matcher2 = regex2.matcher(xml);
			if (matcher2.find()) {
				String DataElements = matcher2.group(1);
				Log.d(TAG, str + ": " + DataElements);

				switch (XML_TAGS.valueOf(str)) {
				case actLongDescr:
					m.activity_description = DataElements;
					break;
				case postalZipCode:
					m.zip_code = DataElements;
					break;
				case facActivity:
					m.activity = DataElements;
					break;
				case registDate:
					m.start_date = DataElements;
					break;
				case stopDate:
					m.end_date = DataElements;
					break;
				case doyDescr:
					m.tax_office_description = DataElements;
					break;
				case parDescription:
					m.area = DataElements;
					break;
				case deactivationFlag:
					m.vat_indication = DataElements;
					break;
				case postalAddressNo:
					m.address_no = DataElements;
					break;
				case postalAddress:
					m.address = DataElements;
					break;
				case doy:
					m.tax_office_code = DataElements;
					break;
				case firmPhone:
					m.telephone = DataElements;
					break;
				case onomasia:
					m.name = DataElements;
					break;
				case firmFax:
					m.fax = DataElements;
					break;
				case afm:
					m.vat = DataElements;
					break;
				case commerTitle:
					m.title = DataElements;
					break;
				}
			}
		}
		return m;
	}
}
