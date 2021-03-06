/*******************************************************************************
 * StockD fetches EOD stock market data from Offical Stock exchange sites
 *     Copyright (C) 2020  Viresh Gupta
 *     More at https://github.com/virresh/StockD/
 * 
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License along
 *     with this program; if not, write to the Free Software Foundation, Inc.,
 *     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 ******************************************************************************/
package common;

public class Queries {
	public static String createSettingsTable() {
		return  "CREATE TABLE STOCKD.SETTINGS (\n" + 
				"    SETTING_ID INTEGER NOT NULL,\n" + 
				"    SETTING_NAME VARCHAR(100) NOT NULL,\n" + 
				"    SETTING_VALUE VARCHAR(100) NOT NULL,\n" + 
				"    SETTING_TYPE VARCHAR(50) DEFAULT 'checkbox' NOT NULL,\n" + 
				"    CATEGORY VARCHAR(100) NOT NULL,\n" + 
				"    SUBCATEGORY VARCHAR(100),\n" + 
				"    PRIMARY KEY (SETTING_ID)\n" + 
				")";
	}
	
	public static String createLinksTable() {
		return  "CREATE TABLE STOCKD.LINKS ( \n" + 
				"    PRODUCT_NAME VARCHAR(100), \n" + 
				"    PRODUCT_CODE VARCHAR(20), \n" + 
				"    PRODUCT_DESCRIPTION VARCHAR(255), \n" + 
				"    PRODUCT_LINK VARCHAR(500),\n" + 
				"    PRIMARY KEY (PRODUCT_CODE)\n" + 
				")";
	}
	
	public static String createBaseLinkTable() {
		return  "CREATE TABLE STOCKD.BASELINK ( \n" + 
				"    BASE_URL VARCHAR(100),\n" + 
				"    STOCK_TYPE VARCHAR(100),\n" + 
				"    PRIMARY KEY (STOCK_TYPE) \n" + 
				")";
	}
	
	public static String updateSetting() {
		return  "UPDATE STOCKD.SETTINGS\n" + 
				"SET\n" + 
				"    SETTING_NAME=:SETTING_NAME,\n" + 
				"    SETTING_VALUE=:SETTING_VALUE, \n" + 
				"    SETTING_TYPE=:SETTING_TYPE, \n" + 
				"    CATEGORY=:CATEGORY, \n" + 
				"    SUBCATEGORY=:SUBCATEGORY\n" + 
				"WHERE\n" + 
				"    SETTING_ID=:SETTING_ID\n" 
				;
	}
	
	public static String updateLink() {
		return  "UPDATE STOCKD.LINKS\n" + 
				"SET\n" + 
				"    PRODUCT_NAME=:PRODUCT_NAME,\n" + 
				"    PRODUCT_DESCRIPTION=:PRODUCT_DESCRIPTION,\n" + 
				"    PRODUCT_LINK=:PRODUCT_LINK\n" + 
				"WHERE\n" + 
				"    PRODUCT_CODE=:PRODUCT_CODE\n"
				;
	}
	
	public static String updateBaseLink() {
		return  "UPDATE STOCKD.BASELINK\n" + 
				"SET\n" + 
				"    BASE_URL=:BASE_URL\n" + 
				"WHERE\n" + 
				"    STOCK_TYPE=:STOCK_TYPE"
				;
	}
	
	public static String insertSetting() {
		return  "INSERT INTO STOCKD.SETTINGS \n" + 
				"    (SETTING_ID, SETTING_NAME, SETTING_VALUE, SETTING_TYPE, CATEGORY, SUBCATEGORY)\n" + 
				"VALUES\n" + 
				"    (:SETTING_ID, :SETTING_NAME, :SETTING_VALUE, :SETTING_TYPE, :CATEGORY, :SUBCATEGORY)\n" 
				;
	}
	
	public static String insertLink() {
		return  "INSERT INTO STOCKD.LINKS\n" + 
				"    (PRODUCT_NAME, PRODUCT_CODE, PRODUCT_DESCRIPTION, PRODUCT_LINK) \n" + 
				"VALUES\n" + 
				"    (:PRODUCT_NAME, :PRODUCT_CODE, :PRODUCT_DESCRIPTION, :PRODUCT_LINK) \n"
				;
	}
	
	public static String insertBaseLink() {
		return  "INSERT INTO STOCKD.BASELINK\n" + 
				"    (BASE_URL, STOCK_TYPE)\n" + 
				"VALUES\n" + 
				"    (:BASE_URL, :STOCK_TYPE)"
				;
	}
	
	public static String readBaseLinks() {
		return "SELECT * FROM STOCKD.BASELINK";
	}
	
	public static String readNormalLinks() {
		return "SELECT * FROM STOCKD.LINKS";
	}
	
	public static String readSettings() {
		return "SELECT * FROM STOCKD.SETTINGS";
	}
}

/*
Raw Queries:
CREATE TABLE STOCKD.LINKS ( 
    PRODUCT_NAME VARCHAR(100), 
    PRODUCT_CODE VARCHAR(20), 
    PRODUCT_DESCRIPTION VARCHAR(255), 
    PRODUCT_LINK VARCHAR(500),
    PRIMARY KEY (PRODUCT_CODE)
);


CREATE TABLE STOCKD.BASELINK ( 
    BASE_URL VARCHAR(100),
    STOCK_TYPE VARCHAR(100),
    PRIMARY KEY (STOCK_TYPE) 
);


CREATE TABLE STOCKD.SETTINGS (
    SETTING_ID INTEGER NOT NULL,
    SETTING_NAME VARCHAR(100) NOT NULL,
    SETTING_VALUE VARCHAR(100) NOT NULL,
    SETTING_TYPE VARCHAR(50) DEFAULT 'checkbox' NOT NULL,
    CATEGORY VARCHAR(100) NOT NULL,
    SUBCATEGORY VARCHAR(100),
    PRIMARY KEY (SETTING_ID)
);



INSERT INTO STOCKD.SETTINGS 
    (SETTING_ID, SETTING_NAME, SETTING_VALUE, SETTING_TYPE, CATEGORY, SUBCATEGORY)
VALUES
    (:SETTING_ID, :SETTING_NAME, :SETTING_VALUE, :SETTING_TYPE, :CATEGORY, :SUBCATEGORY)

UPDATE STOCKD.SETTINGS
SET
    SETTING_NAME=:SETTING_NAME,
    SETTING_VALUE=:SETTING_VALUE, 
    SETTING_TYPE=:SETTING_TYPE, 
    CATEGORY=:CATEGORY, 
    SUBCATEGORY=:SUBCATEGORY
WHERE
    SETTING_ID=:SETTING_ID



INSERT INTO STOCKD.LINKS
    (PRODUCT_NAME, PRODUCT_CODE, PRODUCT_DESCRIPTION, PRODUCT_LINK) 
VALUES
    (:PRODUCT_NAME, :PRODUCT_CODE, :PRODUCT_DESCRIPTION, :PRODUCT_LINK) 

UPDATE STOCKD.LINKS
SET
    PRODUCT_NAME=:PRODUCT_NAME,
    PRODUCT_DESCRIPTION=:PRODUCT_DESCRIPTION,
    PRODUCT_LINK=:PRODUCT_LINK
WHERE
    PRODUCT_CODE=:PRODUCT_CODE



INSERT INTO STOCKD.BASELINK
    (BASE_URL, STOCK_TYPE)
VALUES
    (:BASE_URL, :STOCK_TYPE)

UPDATE STOCKD.BASELINK
SET
    BASE_URL=:BASE_URL
WHERE
    STOCK_TYPE=:STOCK_TYPE
    

SELECT * FROM STOCKD.BASELINK

SELECT * FROM STOCKD.LINKS

SELECT * FROM STOCKD.SETTINGS

*/
