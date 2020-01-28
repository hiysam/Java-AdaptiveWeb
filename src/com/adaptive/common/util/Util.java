package com.adaptive.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.adaptive.common.constant.CommonConstants;
import com.adaptive.common.model.BaseEntity;

public class Util {
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			CommonConstants.INPUT_DATE_FORMAT);

	private static NumberFormat decimalFormatter = new DecimalFormat("#.#");

	public static long MILLIS_IN_DAY = 1000 * 60 * 60 * 24;

	public static String retrieveYearFromPeriod(String period) {
		return StringUtils.substring(period, 0, 4);
	}

	public static String retrieveMonthFromPeriod(String period) {
		return StringUtils.substring(period, 4, 6);
	}

	public static Workbook createWorkbook(String filename, byte[] content)
			throws IOException {
		String ext = Util.getExtensionFromFileName(filename);

		if (StringUtils.equalsIgnoreCase("xls", ext)) {
			return new HSSFWorkbook(new ByteArrayInputStream(content));
		} else if (StringUtils.equalsIgnoreCase("xlsx", ext)) {
			return new XSSFWorkbook(new ByteArrayInputStream(content));
			/*
			 * return StreamingReader.builder() .rowCacheSize(1000) // number of
			 * rows to keep in memory (defaults to 10) .bufferSize(4096) //
			 * buffer size to use when reading InputStream to file (defaults to
			 * 1024) .open(new ByteArrayInputStream(content)); // InputStream or
			 * File for XLSX file (required)
			 */} else {
			throw new RuntimeException("Extension not valid");
		}
	}

	public static void decorateAuditTrailNew(BaseEntity entity, String user) {
		//entity.setActiveFlag(CommonConstants.ACTIVE_FLAG_Y);
		entity.setCreateOn(new Timestamp(new Date().getTime()));
		entity.setCreateBy(user);
	}
	
	public static void decorateAuditTrailUpdate(BaseEntity entity, String user) {
		entity.setUpdateOn(new Timestamp(new Date().getTime()));
		entity.setUpdateBy(user);
	}

	public static String getExtensionFromFileName(String fileName) {
		int lastIdx = StringUtils.lastIndexOf(fileName, ".");
		if (lastIdx >= 0) {
			String ext = StringUtils.substring(fileName, lastIdx + 1);
			return ext;
		} else {
			return "";
		}
	}

	public static void decorateHeaderTableColumnXlsx(Workbook wb, Sheet sheet,
			Row row, List<String> headers) {
		CellStyle headerStyle = Util.buildStyleForHeader(wb);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		for (int i = 0; i < headers.size(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(headers.get(i));
			cell.setCellStyle(headerStyle);
		}

		for (int i = 0; i < headers.size(); i++) {
			sheet.autoSizeColumn(i);
		}
	}

	public static void decorateHeaderLogFileXlsx(Workbook wb, Sheet sheet,
			String processName, Date dateExec) {
		Row row0 = sheet.createRow(0);

		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);

		Cell cell0 = row0.createCell(0);
		cell0.setCellValue("Process : " + processName);
		cell0.setCellStyle(style);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

		Row row1 = sheet.createRow(1);

		Cell cell1 = row1.createCell(0);
		SimpleDateFormat sdf = createDateFormatWithDateAndTime();
		cell1.setCellValue("Date : " + sdf.format(dateExec));
		cell1.setCellStyle(style);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 8));

	}

	public static void createPickListXlsxFromDatas(Sheet sheet, int col,
			String[] datas) {
		createPickListXlsxFromDatas(sheet, 1, col, datas);
	}

	public static void createPickListXlsxFromDatas(Sheet sheet, int startRow,
			int col, String[] datas) {
		if (datas == null || datas.length <= 0) {
			datas = new String[] { StringUtils.EMPTY };
		}
		XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(
				(XSSFSheet) sheet);
		XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
				.createExplicitListConstraint(datas);
		CellRangeAddressList addressList = new CellRangeAddressList(startRow,
				SpreadsheetVersion.EXCEL2007.getMaxRows() - 1, col, col);
		XSSFDataValidation validation = (XSSFDataValidation) dvHelper
				.createValidation(dvConstraint, addressList);
		// validation.setSuppressDropDownArrow(false);
		validation.setShowErrorBox(true);
		sheet.addValidationData(validation);
	}

	public static void createPickListXlsxFromFormula(Sheet sheet, int startRow,
			int col, String formulaName) {
		XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(
				(XSSFSheet) sheet);
		XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
				.createFormulaListConstraint(formulaName);
		CellRangeAddressList addressList = new CellRangeAddressList(startRow,
				SpreadsheetVersion.EXCEL2007.getMaxRows() - 1, col, col);
		XSSFDataValidation validation = (XSSFDataValidation) dvHelper
				.createValidation(dvConstraint, addressList);
		// validation.setSuppressDropDownArrow(false);
		validation.setShowErrorBox(true);
		sheet.addValidationData(validation);
	}

	public static SimpleDateFormat createDateFormatWithDateAndTime() {
		return new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	}

	public static SimpleDateFormat createDateFormatWithDateOnly() {
		return new SimpleDateFormat("dd-MMM-yyyy");
	}

	public static CellStyle buildStyleForDate(Workbook wb, String format) {
		CreationHelper createHelper = wb.getCreationHelper();
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(
				format));

		return cellStyle;
	}

	public static CellStyle buildStyleForMoney(Workbook wb, String format) {
		CreationHelper createHelper = wb.getCreationHelper();
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(
				format));

		return cellStyle;
	}

	public static CellStyle buildStyleForErrorMessage(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		// style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		// style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		Font font = wb.createFont();
		font.setColor(HSSFColor.RED.index);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);

		return style;
	}

	public static CellStyle buildStyleForHeader(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		Font font = wb.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);

		return style;
	}

	public static DecimalFormat createDecimalFormatNoGrouping() {
		DecimalFormat format = (DecimalFormat) NumberFormat
				.getNumberInstance(Locale.ENGLISH);
		format.setGroupingUsed(false);
		return format;
	}

	public static Double getCellValueTypeNumber(Row row, Cell cell) {
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			return null;
		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		default:
			throw new RuntimeException("Format data for not valid [row:"
					+ row.getRowNum() + "][col:" + cell.getColumnIndex()
					+ "], expected number");
		}
	}

	public static Date getCellValueTypeDate(Row row, Cell cell) {
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			return null;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				throw new RuntimeException("Format data for not valid [row:"
						+ row.getRowNum() + "][col:" + cell.getColumnIndex()
						+ "], expected Date");
			}
		default:
			throw new RuntimeException("Format data for not valid [row:"
					+ row.getRowNum() + "][col:" + cell.getColumnIndex()
					+ "], expected Date");
		}
	}

	public static String getCellValueTypeStringOrNum(Row row, Cell cell) {
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			return null;
		case Cell.CELL_TYPE_STRING:
			return (cell.getRichStringCellValue().getString());

		case Cell.CELL_TYPE_NUMERIC:
			return Util.createDecimalFormatNoGrouping().format(
					cell.getNumericCellValue());
		default:
			throw new RuntimeException("Format data for not valid [row:"
					+ row.getRowNum() + "][col:" + cell.getColumnIndex()
					+ "], expected Text or Number");
		}
	}

	public static String getCellStringValue(Cell cell) {
		String value = null;

		if (cell == null) {
			return StringUtils.EMPTY; //""
		}

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				value = dateFormatter.format(cell.getDateCellValue());
			} else {
				value = decimalFormatter.format(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_BLANK:
			value = StringUtils.EMPTY;
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			value = StringUtils.EMPTY;
			break;
		case Cell.CELL_TYPE_FORMULA:
			try {
				value = cell.getStringCellValue();
			} catch (Exception ex) {
				value = StringUtils.EMPTY;
			}
			break;
		default:
			value = StringUtils.EMPTY;
			break;
		}

		return value;
	}

	public static String excelColumnIndexName(int index) {
		StringBuilder s = new StringBuilder();

		while (index >= 26) {
			s.insert(0, (char) ('A' + index % 26));
			index = index / 26 - 1;
		}
		s.insert(0, (char) ('A' + index));

		return s.toString();
	}

	public static Row excelCopyRow(Sheet sheet, int rowSourceIdx, int rowDestIdx) {
		return excelCopyRow(sheet, rowSourceIdx, rowDestIdx, false);
	}

	public static Row excelCopyRow(Sheet sheet, int rowSourceIdx,
			int rowDestIdx, boolean isNotCopyValue) {
		Row rowSource = sheet.getRow(rowSourceIdx);
		Row rowDest = sheet.getRow(rowDestIdx);

		if (rowSource == null) {
			return null;
		}

		if (rowDest != null) {
			sheet.shiftRows(rowDestIdx, sheet.getLastRowNum(), 1);
		} else {
			rowDest = sheet.createRow(rowDestIdx);
		}

		if (rowSource != null) {
			Cell cellSource = null;
			Cell cellDest = null;
			for (int i = 0; i < rowSource.getLastCellNum(); i++) {
				cellSource = rowSource.getCell(i);
				cellDest = rowDest.createCell(i);

				// If the old cell is null jump to next cell
				if (cellSource == null) {
					cellDest = null;
					continue;
				}

				cellDest.setCellType(cellSource.getCellType());
				cellDest.setCellStyle(cellSource.getCellStyle());
				if (cellSource.getCellComment() != null) {
					cellDest.setCellComment(cellSource.getCellComment());
				}
				if (cellSource.getHyperlink() != null) {
					cellDest.setHyperlink(cellSource.getHyperlink());
				}

				if (!isNotCopyValue) {
					switch (cellSource.getCellType()) {
					case Cell.CELL_TYPE_BLANK:
						cellDest.setCellValue(cellSource.getStringCellValue());
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						cellDest.setCellValue(cellSource.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_ERROR:
						cellDest.setCellErrorValue(cellSource
								.getErrorCellValue());
						break;
					case Cell.CELL_TYPE_FORMULA:
						cellDest.setCellFormula(cellSource.getCellFormula());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cellDest.setCellValue(cellSource.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						cellDest.setCellValue(cellSource
								.getRichStringCellValue());
						break;
					}
				}
			}
		}

		return rowDest;
	}

	public static String excelGetCellStringValue(Cell cell) {
		return excelGetCellStringValue(cell, null, null);
	}

	public static String excelGetCellStringValue(Cell cell,
			SimpleDateFormat dateFormat, NumberFormat decimalFormat) {
		String value = null;

		if (dateFormat == null) {
			dateFormat = dateFormatter;
		}

		if (decimalFormat == null) {
			decimalFormat = decimalFormatter;
		}

		if (cell == null) {
			return StringUtils.EMPTY;
		}

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				value = dateFormat.format(cell.getDateCellValue());
			} else {
				value = decimalFormat.format(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_BLANK:
			value = StringUtils.EMPTY;
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			value = StringUtils.EMPTY;
			break;
		case Cell.CELL_TYPE_FORMULA:
			try {
				value = cell.getStringCellValue();
			} catch (Exception ex) {
				value = StringUtils.EMPTY;
			}
			break;
		default:
			value = StringUtils.EMPTY;
			break;
		}

		return value;
	}

	public static String getYYYYMMFromDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(date);
	}

	public static Date getStartDateMonth(String periode)
			throws java.text.ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.parse(periode);
	}

	public static Date getLastDateMonth(Date startPeriod) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startPeriod);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		return cal.getTime();
	}

	public static int getSumDayInPeriod(String periodyyyymm) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date d;

		try {
			d = sdf.parse(periodyyyymm);
		} catch (ParseException e) {
			throw new RuntimeException("Periode not valid [" + periodyyyymm
					+ "]");
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static String retrieveGradeCodeFromFullCode(
			String selectedGradeFullCode) {
		int idxPeriod = selectedGradeFullCode.indexOf(".");
		return selectedGradeFullCode.substring(0, idxPeriod);
	}

	public static String addPeriode(String periodyyyymm, int add) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date d;
		try {
			d = sdf.parse(periodyyyymm);

		} catch (Exception ex) {
			throw new RuntimeException("Error parsing period [" + periodyyyymm
					+ "]");
		}

		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MONTH, add);
		d = c.getTime();

		return sdf.format(d);
	}

	/**
	 * Calculate diff between two date in months. rounding calculation to
	 * months. Example : 20120120 - 20130120 : 12 months 20120120 - 20130101 :
	 * 12 months 20120120 - 20130131 : 12 months 20120120 - 20120131 : 0 months
	 * 20121110 - 20130514 : 6 months 20120210 - 20130914 : 19 months
	 * 
	 * @param date2
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Integer calculateDiffMonth(Date date1, Date date2) {
		// Modified by Neir Kate 25 Nov 2015 - This is possible
		// Cause Masa Kerja in Year is not from beginning, but join date, while
		// employee has not yet join
		if (date2.getTime() < date1.getTime()) {
			// throw new RuntimeException("Date 1 [" + date1 +
			// "] is later than date 2[" + date2 + "]"); // Commented
			return 0; // Added
		}
		int y1 = date1.getYear();
		int y2 = date2.getYear();

		int m1 = date1.getMonth();
		int m2 = date2.getMonth();

		if (y1 != y2) {
			if (m2 >= m1) {
				int diffY = y2 - y1;
				return (diffY * 12) + (m2 - m1);
			} else {
				int diffY = y2 - y1;
				return (diffY * 12) - (m1 - m2);
			}
		} else {
			return m2 - m1;
		}

	}

	/**
	 * calculation diff in year Example : 20120120 - 20130120 : 1 year 20120120
	 * - 20130119 : 0 year 20120210 - 20130119 : 0 year 20120119 - 20130120 : 1
	 * year
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Integer calculateDiffYear(Date date1, Date date2) {
		if (date2.getTime() < date1.getTime()) {
			// Modified by Neir Kate 25 Nov 2015 - This is possible
			// Cause Masa Kerja in Year is not from beginning, but join date,
			// while employee has not yet join
			// throw new RuntimeException("Date 1 [" + date1 +
			// "] is later than date 2[" + date2 + "]"); // Commented
			return 0; // Added by Neir Kate 25 Nov 2015
		}

		int predictYearDiff = date2.getYear() - date1.getYear();
		Date datePredictFullYear = addYear(date1, predictYearDiff);

		if (datePredictFullYear.getTime() < date2.getTime()) {
			Date loopDate;
			for (int i = 1;; i++) {
				loopDate = addYear(date1, predictYearDiff + i);
				if (loopDate.getTime() > date2.getTime()) {
					return predictYearDiff + i - 1;
				}
			}

		} else if (datePredictFullYear.getTime() > date2.getTime()) {
			Date loopDate;
			for (int i = 1;; i++) {
				loopDate = addYear(date1, predictYearDiff - i);
				if (loopDate.getTime() < date2.getTime()) {
					return predictYearDiff - i;
				}
			}

		} else {
			return predictYearDiff;
		}

		// xxx
		//
		// Date dateOneMinus1Day = minusOneDate(date1);
		//
		// return diff(dateOneMinus1Day, date2);
		//
		// Date oneYearFromD1 = oneYear(date1);
		// int diff = 0;
		// if (oneYearFromD1.getTime() <= date2.getTime()) {
		// diff++;
		// if (oneYearFromD1.getTime() < date2.getTime()) {
		// diff += diff(oneYearFromD1, date2);
		// }
		// return diff;
		// } else {
		// return 0;
		// }

	}

	// Added by Neir Kate 25 Nov 2016 - Start - Want to find exact years
	// difference
	// return 0 otherwise (e.g. more or equal to 1 month difference(s))
	public static Integer calculateDiffYearExactly(Date date1, Date date2) {
		if (date2.getTime() < date1.getTime()) {
			return 0;
		}

		Calendar clone = Calendar.getInstance();
		clone.setTime(date1);
		clone.set(Calendar.DAY_OF_MONTH, 1);

		Calendar now = Calendar.getInstance();
		now.setTime(date2);
		now.set(Calendar.DAY_OF_MONTH, 1);

		int years = 0;
		int countMonth = 0;
		while (!clone.equals(now)) {
			clone.add(Calendar.MONTH, 1);
			countMonth++;
			if (countMonth == 12) {
				years++;
				countMonth = 0;
			}
		}
		if (countMonth != 0) {
			years = 0;
		}

		return years;
	}

	// Added by Neir Kate 25 Nov 2016 - End - Year exact without even more than
	// 1 month
	private static Date addYear(Date d, int predictYearDiff) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.YEAR, predictYearDiff);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();

	}

	// private static boolean isFirstDateOfYear(Date d) {
	// if (d.getDate() == 1 && d.getMonth() == 0) {
	// return true;
	// } else {
	// return false;
	// }
	// }
	//
	// private static Date minusOneDate(Date d) {
	// Calendar cal = Calendar.getInstance();
	// cal.setTime(d);
	// cal.add(Calendar.DATE, -1);
	// return cal.getTime();
	//
	// }
	//
	// private static boolean isKabisatFebruari29(Date d) {
	// if (d.getDate() == 29 && d.getMonth() == 1) {
	// return true;
	// } else {
	// return false;
	// }
	// }

	@SuppressWarnings("deprecation")
	public static int diff(Date date1, Date date2) {
		int y1 = date1.getYear();
		int y2 = date2.getYear();

		int m1 = date1.getMonth();
		int m2 = date2.getMonth();

		Integer diffY = y2 - y1 - 1;
		if (m2 > m1) {
			diffY += 1;
		} else if (m2 < m1) {

		} else if (m2 == m1) {
			int d1 = date1.getDate();
			int d2 = date2.getDate();

			int lastDayOfMonth = getLastDateMonth(date2).getDate();
			if (d2 == lastDayOfMonth) {
				diffY += 1;
			} else if (d1 <= d2) {
				diffY += 1;
			} else if (d1 > d2) {

			}
		}
		return diffY;

	}

	// private static Date addOneDay(Date d) {
	// Calendar cal = Calendar.getInstance();
	// cal.setTime(d);
	// cal.add(Calendar.DATE, 1);
	// return cal.getTime();
	// }
	//
	// private static Date oneYear(Date d1) {
	// Calendar cal = Calendar.getInstance();
	// cal.setTime(d1);
	// cal.add(Calendar.YEAR, 1);
	// cal.add(Calendar.DATE, -1);
	// return cal.getTime();
	// }

	public static Integer calculateDiffDays(Date date1, Date date2) {
		long millisDiff = date2.getTime() - date1.getTime();

		return (int) (millisDiff / MILLIS_IN_DAY);
	}

	public static Date truncateDate(Date date) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public static String convertNumberOfMonthsToReadable(Integer months,
			String textMonths, String textYears) {
		String result = StringUtils.EMPTY;

		if (months == null) {
			return null;
		}

		if (months == 0) {
			return "0 " + textYears + " 0 " + textMonths;
		}

		Integer years = (int) Math.floor((double) months / (double) 12);
		if (years != 0) {
			result += String.format("%d %s", years, textYears);
		}

		if (months % 12 != 0) {
			if (!StringUtils.isEmpty(result)) {
				result += " ";
			}
			result += String.format("%d %s", months % 12, textMonths);
		}

		return result;
	}

	public static String[] stringSplitByNewline(String str,
			Integer becomeRowCount) {
		String[] arrResult = new String[becomeRowCount];

		if (str == null || becomeRowCount == null) {
			for (int i = 0; i < arrResult.length; i++) {
				arrResult[i] = StringUtils.EMPTY;
			}

			return arrResult;
		}

		String[] arrStrTemp = str.split(System.getProperty("line.separator"));

		List<String> list = new ArrayList<String>(Arrays.asList(arrStrTemp));
		list.removeAll(Collections.singleton(StringUtils.EMPTY));

		String[] arrStr = list.toArray(new String[list.size()]);

		if (arrStr.length < becomeRowCount) {
			arrResult[0] = str;

			for (int i = 1; i < arrResult.length; i++) {
				arrResult[i] = StringUtils.EMPTY;
			}

			return arrResult;
		}

		int join = arrStr.length / becomeRowCount;
		int arrResultIndex = 0;
		int counter = 0;

		for (int i = 0; i < arrStr.length; i++) {
			if (counter >= join) {
				counter = 0;
				if (arrResultIndex < arrResult.length - 1) {
					arrResultIndex++;
				}
			}

			arrResult[arrResultIndex] = ((arrResult[arrResultIndex] != null) ? (arrResult[arrResultIndex] + " ")
					: "")
					+ arrStr[i];

			counter++;
		}

		return arrResult;
	}

	public static String getFullUrl(String url) {
		return "/faces/module/insurance/" + (url != null ? url : "");
	}

}
