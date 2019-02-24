package ejercicio1.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	public static final String FORMATO_FECHA = "yyyy-MM-dd";
	public static final String FORMATO_FECHA_HORA_MINUTOS = "yyyy-MM-dd hh:mm";
	public static final String FORMATO_FECHA_HORA_MINUTOS_SEGUNDOS = "yyyy-MM-dd hh:mm:ss";

	/* Este método permite obtener a partir de un String una fecha */
	public static Date fromStringToDate(String fecha) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA);
		return fromStringToDate(fecha, dateFormat);
	}

	public static Date fromStringToDateTime(String fecha) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA_HORA_MINUTOS);
		return fromStringToDate(fecha, dateFormat);
	}

	public static Date fromStringToDateTime2(String fecha) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA_HORA_MINUTOS_SEGUNDOS);
		return fromStringToDate(fecha, dateFormat);
	}

	private static Date fromStringToDate(String fecha, SimpleDateFormat dateFormat) {
		Date utilDate = null;

		if (fecha != null && !fecha.equals("")) {
			try {
				utilDate = dateFormat.parse(fecha);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return utilDate;
	}

	public static String fromDateToString(Date fecha) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA);
		String fechaString = "";
		
		if (fecha != null) {
			fechaString = dateFormat.format(fecha);
		}
		
		return fechaString;
	}
	
	public static String fromDateTimeToString(Date fecha) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA_HORA_MINUTOS_SEGUNDOS);
		String fechaString = "";
		
		if (fecha != null) {
			fechaString = dateFormat.format(fecha);
		}
		
		return fechaString;
	}
	
	public static String fromDateToString(Date fecha, String formato) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
		String fechaString = "";
		
		if (fecha != null) {
			fechaString = dateFormat.format(fecha);
		}
		
		return fechaString;
	}
}
