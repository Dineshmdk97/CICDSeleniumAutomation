package seleniumFramework.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {

	public List<HashMap<String, String>> getJsonDataToMap() throws IOException
	{
		//Json to String
		String jsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\src\\test\\java\\seleniumFramework\\data\\PurchaseOrder.json"), StandardCharsets.UTF_8);
		//String to HashMap - using Jackson Databind
		
		//Paste Jackson Databind in pom.xml file
		//https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind/2.15.0
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>()
		{
		});
		return data;
		}
	}

