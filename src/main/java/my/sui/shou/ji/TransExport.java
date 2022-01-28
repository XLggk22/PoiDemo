package my.sui.shou.ji;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * 返回码映射处理成sql
 *
 */
public class TransExport {
	
	public static void main(String[] args) {
		TransExport standerCode = new TransExport();
		String excelParentPath= "I:\\坚果云同步\\我的坚果云\\财务管理系统\\随手记\\微信支付账单(20200101-20200131)\\";
		String importFilePath = excelParentPath + "处理数据.xlsx";
		String exportFilePath = excelParentPath + "处理数据_处理后.xlsx";

		standerCode.dealExcel(0, 11,importFilePath,exportFilePath);
	}
	
	public void dealExcel(int sheetIndex,int cellIndex,String importFilePath,String exportFilePath){
		//数据创建时间
		try {
            Workbook excelFile = WorkbookFactory.create(new File(importFilePath));
            Sheet sheet = excelFile.getSheetAt(0);//商户排序表
            Sheet sheet2 = excelFile.getSheetAt(1);//处理数据表
            System.out.println(sheet.getSheetName());
            Iterator<Row> iterator = sheet.iterator();

            while(iterator.hasNext()) {
                Row row = iterator.next();
                int rowNum = row.getRowNum();
                if(rowNum >=1){
//                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                    TransObject transObject = new TransObject();
                    transObject.setTransTime(row.getCell(0).getDateCellValue());
                    transObject.setTransType(row.getCell(1).getStringCellValue());
                    transObject.setMerchant(row.getCell(2).getStringCellValue());
                    transObject.setGoods(row.getCell(3).getStringCellValue());
                    transObject.setCleanType(row.getCell(4).getStringCellValue());
                    transObject.setTransAmt(row.getCell(5).getStringCellValue());
                    transObject.setPayMethod(row.getCell(6).getStringCellValue());
                    transObject.setTransStatus(row.getCell(7).getStringCellValue());
                    transObject.setTransOrder(row.getCell(8).getStringCellValue());
                    transObject.setMerchantOrder(row.getCell(9).getStringCellValue());

                    System.out.println(transObject.toString());

                    System.out.println("rowNum:"+rowNum);
//                    Row row2 = sheet2.getRow(rowNum);
                    Row row2 = sheet2.createRow(rowNum);

                    //交易类型
                    row2.createCell(0).setCellValue(transObject.getCleanType());
                    //交易时间
                    row2.createCell(1).setCellValue(transObject.getTransTime());
                    Classify classify = getClassify(transObject);
                    //分类
                    row2.createCell(2).setCellValue(classify.getType());
                    //子分类
                    row2.createCell(3).setCellValue(classify.getSubType());
                    Account account = getAccount(transObject);
                    //账户
                    row2.createCell(4).setCellValue(account.getAccount());
                    //子账户
                    row2.createCell(5).setCellValue(account.getSubAccount());
                    //金额
                    row2.createCell(6).setCellValue(transObject.getTransAmt());
                    //交易类型
                    row2.createCell(11).setCellValue(transObject.getTransType());
                    //交易对方
                    row2.createCell(12).setCellValue(transObject.getMerchant());
                    //商品
                    row2.createCell(13).setCellValue(transObject.getGoods());
                    //交易金额
                    row2.createCell(14).setCellValue(transObject.getTransAmt());
                    //支付方式
                    row2.createCell(15).setCellValue(transObject.getPayMethod());
                }
              }
//              excelFile.write(new FileOutputStream("I:\\坚果云同步\\我的坚果云\\财务管理系统\\随手记\\微信支付账单(20190901-20190930)\\11.xlsx"));
              excelFile.write(new FileOutputStream(new File(exportFilePath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

    /**
     * 获取分类`
     * @param transObject
     * @return
     */
	private static Classify getClassify(TransObject transObject){

        String cleanType = transObject.getCleanType();
        if (cleanType.contains("支出")){
            String merchant = transObject.getMerchant();
            if (isNotEmpty(merchant) && (merchant.contains("妈妈说了算")
                    || merchant.contains("檬运动")
                    || merchant.contains("餐饮")
                    || merchant.contains("黄焖鸡")
                    || merchant.contains("猪脚饭")
                    || merchant.contains("麻辣烫")
                    || merchant.contains("木桶饭")
                    || merchant.contains("快餐")
                    || merchant.contains("沙县"))){
                return Classify.OUT_SHIPING_EAT;
            }
            if (isNotEmpty(merchant) && (merchant.contains("地铁")
                    || merchant.contains("单车"))
                    || merchant.contains("羊城通")
                    || merchant.contains("深圳通")){
                return Classify.OUT_XINGCHE_GONGJIAO;
            }
            if (isNotEmpty(merchant) && merchant.contains("医院")
                || merchant.contains("药")){
                return  Classify.OUT_YILIAO_YAOPING;
            }
            if (isNotEmpty(merchant) && merchant.contains("真善美")){
                return Classify.OUT_JUJIA_FANGZHU;
            }

        }else if(cleanType.contains("收入")){
            String transType = transObject.getTransType();
            if (isNotEmpty(transType) && transType.contains("微信红包")){
                return Classify.IN_OTHER_HONGBAO;
            }
        }

        return Classify.DEFAULT_EMPTY;
    }


    /**
     * 获取账户
     * @param transObject
     * @return
     */
    private static Account getAccount(TransObject transObject){
        String transType = transObject.getTransType();
        if (isNotEmpty(transType) && transType.contains("微信红包")){
            return Account.VITTUAL_WEIXIN;
        }

        String payMethod = transObject.getPayMethod();
        if (isNotEmpty(payMethod) && payMethod.contains("招商银行")){
            return Account.CREDIT_ZHAOHANG;
        }
        if (isNotEmpty(payMethod) && payMethod.contains("平安银行")){
            return Account.CARD_PINGAN;
        }
        if (isNotEmpty(payMethod) && payMethod.contains("零钱")){
            return Account.VITTUAL_WEIXIN;
        }

        return Account.DEFAULT_EMPTY;
    }

    private static boolean isNotEmpty(String value){
        return !( null == value || "".equals(value));
    }
}
