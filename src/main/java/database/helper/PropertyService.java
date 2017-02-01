package database.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс для загрузки файла конфигурации и извлечения из него различных данных
 */
@SuppressWarnings("UnusedDeclaration")
public final class PropertyService {

    /**
     * Путь к файлу конфигурации
     */
    private static final String CONFIG_FILE_PATH = "db.properties";

    /**
     * Параметры, загруженные из файла
     */
    private final Properties properties;

    /**
     * Экзмепляр Singleton-класса {@link PropertyService}
     */
    private static PropertyService instance;

    /**
     * Создает, если не был создан ранее, и возвращает экзмепляр {@link PropertyService}
     *
     * @return экземпляр {@link PropertyService}
     */
    public static PropertyService getInstance() throws IOException {
        if (instance == null) {
            instance = new PropertyService();
        }
        return instance;
    }

    /**
     * Конструктор загружает конфигурацию из файла, считывает из нее параметры и записывает в соответсвующие поля.
     */
    private PropertyService() throws IOException {
        properties = new Properties();
        try (InputStream stream = PropertyService.class.getClassLoader().getResourceAsStream(CONFIG_FILE_PATH)) {
            properties.load(stream);
        } catch (IOException e) {
            System.err.printf("Ошибка загрузки файла конфигурации '%s'%n", CONFIG_FILE_PATH);
            throw e;
        }
    }

    /**
     * @return полное имя класса используемого JDBC драйвера
     */
    public String getJDBCDriverClassName() {
        return properties.getProperty("JDBCDriverClassName");
    }

    /**
     * @return URL базы данных в формате jdbc:subprotocol:subname
     */
    public String getConnectionURL() {
        return properties.getProperty("ConnectionURL");
    }

    /**
     * @param key ключ параметра
     * @return значение параметра по заданному ключу
     */
    public String getCustomProperty(String key) {
        return properties.getProperty(key);
    }
}
