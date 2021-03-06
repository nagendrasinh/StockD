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
package parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import common.Constants;
import common.RunContext;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvReadOptions;
import tech.tablesaw.io.csv.CsvWriteOptions;

public class ParseFO extends BaseConverter {

	public ParseFO(String save_dir) throws IOException {
		super(save_dir, "FU");
	}
	
	public String add_prefixes(String input, int count) {
		String v = "";
		for(int i=0; i<count; i++) {
			v = v + "I";
		}
		if(RunContext.getContext().FO_add_I_predix()) {
			return v + "-" + input;
		}
		else {
			return input + "-" + v;
		}
	}

	@Override
	public Table parse(String filePath) throws Exception {
		File f = Paths.get(filePath).toFile();
		InputStream instream = new FileInputStream(f);
		
		ColumnType[] columnTypes = {
				ColumnType.STRING,     // 0     INSTRUMENT  
				ColumnType.STRING,     // 1     SYMBOL      
				ColumnType.SKIP, // 2     EXPIRY_DT   
				ColumnType.SKIP,     // 3     STRIKE_PR   
				ColumnType.SKIP,     // 4     OPTION_TYP  
				ColumnType.STRING,     // 5     OPEN        
				ColumnType.STRING,     // 6     HIGH        
				ColumnType.STRING,     // 7     LOW         
				ColumnType.STRING,     // 8     CLOSE       
				ColumnType.SKIP,     // 9     SETTLE_PR   
				ColumnType.SKIP,    // 10    CONTRACTS   
				ColumnType.STRING,     // 11    VAL_INLAKH  
				ColumnType.STRING,    // 12    OPEN_INT    
				ColumnType.SKIP,    // 13    CHG_IN_OI   
				ColumnType.STRING,     // 14    TIMESTAMP   
				ColumnType.SKIP,     // 15    C15         
		};

		Table df = Table
				   .read()
				   .usingOptions(
						   CsvReadOptions
						   .builder(instream)
						   .columnTypes(columnTypes))
				   .retainColumns("SYMBOL", "TIMESTAMP", "OPEN", "HIGH", "LOW", "CLOSE", "VAL_INLAKH", "OPEN_INT", "INSTRUMENT");

		df = df.dropWhere(df.column("INSTRUMENT")
				.asStringColumn()
				.upperCase()
				.isNotIn("FUTIDX", "FUTSTK"));
		
		df.removeColumns(df.columnIndex("INSTRUMENT"));
		
		StringColumn x = df.column("TIMESTAMP")
						   .asStringColumn()
						   .map(Constants::convertDDMMMYYYYtoYYYYMMDD);
		df.replaceColumn("TIMESTAMP", x);
		
		// add prefixes/suffixes to FO Symbols
		String previous = null;
		int count = 0;
		for(int i=0; i<df.rowCount(); i++) {
			String in = df.row(i).getString("SYMBOL");
			if(previous==null || !previous.equals(in)) {
				previous = in;
				count = 0;
			}
			count++;
			df.row(i).setString("SYMBOL", add_prefixes(in, count));
		}

		String suffix = filePath.substring(filePath.length()-17, filePath.length()-8);
		String res = Paths
					  .get(this.directory, 
							  System.getProperty("file.separator"), 
							  this.prodcode + "_" + suffix + ".txt")
					  .toString();
		
		df.column("VAL_INLAKH").setName("VOLUME");
		df.column("OPEN_INT").setName("OPEN INTEREST");
		
		CsvWriteOptions opts = CsvWriteOptions
								.builder(res)
								.separator(',')
								.header(false)
								.build();
		
//		System.out.println(df.structure());
//		System.out.println(df.first(4));
//		System.out.println(res);
		df.write().usingOptions(opts);
		return df;
	}
}
