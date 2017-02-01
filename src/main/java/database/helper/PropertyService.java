package database.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс для загрузки файла конфигурации и извлечения из него различных данных
 */
@SuppressWarnings("UnusedDeclaration")
public class PropertyService {

    /**
     * Путь к файлу конфигурации
     */
    private static final String CONFIG_FILE_PATH = "db.properties";
    /**
     * Параметры, загруженные из файла
     */
    private static Properties properties;

    /**
     * Метод загружает конфигурацию из файла, и возвращает эту конфигурацию. Загрузка происходит однажды,
     * при последующих вызовах метод возвращает уже загруженную ранее конфигурацию.
     *
     * @return возвращает параметры, загруженные из файла конфигурации
     */
    private static Properties getProperties() throws IOException {
        if (properties == null) {
            properties = new Properties();
            try (InputStream stream = PropertyService.class.getClassLoader().getResourceAsStream(CONFIG_FILE_PATH)) {
                properties.load(stream);
            }
        }

        return properties;
    }

    /**
     * @return полное имя класса реализации DAOFactory
     */
    public String getDAOFactoryClassName() throws IOException {
        return getProperties().getProperty("DAOFactoryClassName");
    }

    /**
     * @return полное имя класса используемого JDBC драйвера
     */
    public String getJDBCDriverClassName() throws IOException {
        return getProperties().getProperty("JDBCDriverClassName");
    }

    /**
     * @return URL базы данных в формате jdbc:subprotocol:subname
     */
    public String getConnectionURL() throws IOException {
        return getProperties().getProperty("ConnectionURL");
    }
}
