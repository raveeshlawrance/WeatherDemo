package com.twitter.weather.appdemo.testcases;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twitter.weather.appdemo.WeatherActivity;
import com.twitter.weather.appdemo.model.forecastlist.ForecastResponse;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class StandardDeviationTestCase {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void standardDeviationCorrect() throws IOException {
        ForecastResponse forecastResponse = getForecastData("forecaset_model_data.json");
        double actual = ForecastResponse.getStandardDeviation(forecastResponse.getList());
        double expected = 2.19;
        assertEquals("Getting Standard Deviation value Test Case is Success", expected, actual, 0);
    }

    public ForecastResponse getForecastData (String filename) throws IOException {
        Gson gson = new GsonBuilder().create();
        ForecastResponse forecastResponse = gson.fromJson(readJsonFile(filename),
                ForecastResponse.class);
        return forecastResponse;
    }

    private static File getFileFromPath(Object obj, String fileName) {
        ClassLoader classLoader = obj.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return new File(resource.getPath());
    }

    /*@Test
    public void fileObjectShouldNotBeNull() throws Exception {
        File file = getFileFromPath(this, "androidTest/resources/forecaset_model_data.json");
        assertThat(file, notNullValue());
    }*/

    @Test
    public void fileObjectShouldNotBeNull() throws Exception {
        MatcherAssert.assertThat(getForecastData("forecaset_model_data.json"), notNullValue());
    }

    public String readJsonFile (String filename) throws IOException {
        String ASSET_BASE_PATH = "src/test/resources/";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ASSET_BASE_PATH + filename)));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }
        return sb.toString();
    }
}
