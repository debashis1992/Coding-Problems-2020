package designPatterns.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ObservablePatternTest {
    public static void main(String[] args) {

//        WeatherDataObject weatherDataObject=new WeatherDataObject(1,2,3);
//
//        CurrentConditionsDisplay currentConditionsDisplay=new CurrentConditionsDisplay();
//        currentConditionsDisplay.weatherDataObject=weatherDataObject;
//
//        StatsDisplay statsDisplay=new StatsDisplay();
//        statsDisplay.weatherDataObject=weatherDataObject;
//
//        ForecastDisplay forecastDisplay=new ForecastDisplay();
//        forecastDisplay.weatherDataObject=weatherDataObject;
//
//        DisplayObserver displayObserver=new DisplayObserver();
//        displayObserver.addObserver(currentConditionsDisplay);
//        displayObserver.addObserver(statsDisplay);
//        displayObserver.addObserver(forecastDisplay);
//
//        WeatherPublisher weatherPublisher=new WeatherPublisher(displayObserver);
//        weatherDataObject.setWeatherPublisher(weatherPublisher);
//
//
//        weatherDataObject.setHumidity(19);
//
//        System.out.println("-------------");
//        displayObserver.removeObserver(forecastDisplay);
//
//
//        RainDisplay rainDisplay=new RainDisplay();
//        rainDisplay.weatherDataObject=weatherDataObject;
//        displayObserver.addObserver(rainDisplay);
//        weatherDataObject.setHumidity(99);

    }
}




class WeatherDataObject {
    private long temperature, humidity, pressure;
    WeatherPublisher weatherPublisher;

    public WeatherDataObject(long temperature, long humidity, long pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public void setTemperature(long temperature) {
        this.temperature = temperature;
        measurementChange();
    }

    public void setHumidity(long humidity) {
        this.humidity = humidity;
        measurementChange();
    }

    public void setPressure(long pressure) {
        this.pressure = pressure;
        measurementChange();
    }

    public void setWeatherPublisher(WeatherPublisher weatherPublisher) {
        this.weatherPublisher = weatherPublisher;
    }

    public void measurementChange() {
        if(weatherPublisher!=null)
            weatherPublisher.publishChange();
        else {
            System.out.println("weather publisher not set...");
        }
    }
}

interface UpdatableDisplay {
    void updateAndDisplay();
}

class CurrentConditionsDisplay implements UpdatableDisplay {
    WeatherDataObject weatherDataObject;
    public void updateAndDisplay() {
        System.out.println("updating from current conditions display");
    }
}

class StatsDisplay implements UpdatableDisplay {
    WeatherDataObject weatherDataObject;
    public void updateAndDisplay() {
        System.out.println("updating from stats display");
    }
}

class ForecastDisplay implements UpdatableDisplay {
    WeatherDataObject weatherDataObject;
    public void updateAndDisplay() {
        System.out.println("updating from forecast conditions display");
    }
}

class NewConditionsDisplay implements UpdatableDisplay {
    WeatherDataObject weatherDataObject;
    public void updateAndDisplay() {
        System.out.println("updating from new conditions");
    }
}

class RainDisplay implements UpdatableDisplay {
    WeatherDataObject weatherDataObject;
    public void updateAndDisplay() {
        System.out.println("updating from rain display");
    }
}

class DisplayObserver {
    List<UpdatableDisplay> updatableDisplaySubscriberList;

    public DisplayObserver() {
        this.updatableDisplaySubscriberList = new ArrayList<>();
    }

    void addObserver(UpdatableDisplay updatableDisplay) {
        updatableDisplaySubscriberList.add(updatableDisplay);
    }

    void removeObserver(UpdatableDisplay updatableDisplay) {
        updatableDisplaySubscriberList.remove(updatableDisplay);
    }

    public List<UpdatableDisplay> getUpdatableDisplaySubscriberList() {
        if(updatableDisplaySubscriberList == null)
            return new ArrayList<>();
        return updatableDisplaySubscriberList;
    }
}

class WeatherPublisher {
    DisplayObserver displayObserver;

    public WeatherPublisher(DisplayObserver displayObserver) {
        this.displayObserver = displayObserver;
    }

    public void publishChange() {
        displayObserver.getUpdatableDisplaySubscriberList().forEach(UpdatableDisplay::updateAndDisplay);
    }
}