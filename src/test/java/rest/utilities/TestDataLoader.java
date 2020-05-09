package rest.utilities;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


public class TestDataLoader {

	private String baseUri;
	private Map<String, String> basePath;
	private Map<String, String> queryParams;
	private List<String> searchValues;
	private Map<String, String> sites;

	public static TestDataLoader load(String fileName) {
		TestDataLoader data = null;
		try (InputStream in = TestDataLoader.class.getClassLoader().getResourceAsStream(fileName.concat(".yml"))) {
			data = new Yaml().load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public String getBaseUri() {
		return baseUri;
	}

	public void setBaseUri(String baseUri) {
		this.baseUri = baseUri;
	}
	

	public Map<String, String> getBasePath() {
		return basePath;
	}

	public void setBasePath(Map<String, String> path) {
		this.basePath = path;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Map<String, String> queryParam) {
		this.queryParams = queryParam;
	}

	public List<String> getSearchValues() {
		return searchValues;
	}

	public void setSearchValues(List<String> searchValues) {
		this.searchValues = searchValues;
	}

	public Map<String, String> getSites() {
		return sites;
	}

	public void setSites(Map<String, String> sites) {
		this.sites = sites;
	}

	public String getCurrentSite() {
		return sites.get(StringUtils.defaultIfBlank(System.getProperty("site"), "arg"));
	}
}
