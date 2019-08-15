package my.test.apache.PoiDemo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 返回码映射处理成sql
 *
 */
public class StanderCode {
	
	public static void main(String[] args) {
		StanderCode standerCode = new StanderCode();
		String excelPath = "E:/工作文档/项目文件/pcs系统/接口文档/标准返回码支付V2.0.xlsx";
		String exportPath = "E:/工作文档/项目文件/pcs系统/接口文档/标准返回码/";
		String channelCode = "BFBFC";
		String channelName = "百付宝全卡要素";
		standerCode.dealExcel(0, 3, excelPath, exportPath,channelCode,channelName);
	}
	
	public void dealExcel(int sheetIndex,int cellIndex,String excelPath,String exportPath,String channelCode,String channelName){
		//数据创建时间
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(date);
		try {
			Workbook workbook = WorkbookFactory.create(new File(excelPath));
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			Iterator<Row> iterator = sheet.iterator();
			Set<String> set = new HashSet<>();
			//导出sql
			File exportDir = new File(exportPath);
			if(!exportDir.exists()){
				exportDir.mkdirs();
			}
			Cell channelCell = sheet.getRow(0).getCell(cellIndex);
			channelCell.setCellType(Cell.CELL_TYPE_STRING);
			String channel = channelCell.getStringCellValue();
			System.out.println("处理的渠道："+channel);
			String exportFileName = "StanderCode"+channel+".sql";
			File exportFile = new File(exportDir, exportFileName);
//			exportFile.createNewFile();
			OutputStream os = new FileOutputStream(exportFile);
			//总的配置条数
			int countConf = 0;
			while(iterator.hasNext()){
				Row row = iterator.next();
				if(row.getRowNum()>=1){
					//该渠道没有这一类返回码
					if(row.getCell(cellIndex)==null){
						continue;
					}else{
						row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
					}
					//标准码
					row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					
					String standardCode = row.getCell(1).getStringCellValue();
					String standardName = row.getCell(2).getStringCellValue();
					String ChannelReturn = row.getCell(cellIndex).getStringCellValue();
					String[] split = ChannelReturn.split("\n");
					List<String> ChannelReturnList = Arrays.asList(split);
					for (String config : ChannelReturnList) {
						if("".equals(config.trim())) continue;
						String returnCode = config.substring(0, config.indexOf(" ")).trim();
						String returnName = config.substring(config.indexOf(" "), config.length()).trim();
						String sqlValue = "null, '"+standardCode+"', '"+standardName+"', '"+channelCode+"', '"+channelName+"', '"+returnCode+"', '"+returnName+"', now(),now()";
						String sql = "INSERT INTO `pcs`.`t_channel_standard_config` (`id`, `standard_code`, `standard_name`, `channel_id`, `channel_name`, `return_code`, `return_name`, `create_datetime`, `update_datetime`) VALUES ("+sqlValue+");";
						Pattern p = Pattern.compile("\n");
						Matcher m = p.matcher(sql); 
						sql = m.replaceAll("");
						sql = sql+"\n";
						os.write(sql.getBytes());
					}
					countConf += ChannelReturnList.size();
					System.out.println(standardCode+standardName+"-"+ChannelReturnList.size()+"条");
					System.out.println(ChannelReturnList);
				}
			}
			System.out.println("-------------->总配置条数:"+countConf);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
	}
}
