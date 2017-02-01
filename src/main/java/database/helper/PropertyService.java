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
    private String daoFactoryClassName;
    private String jdbcDriverClassName;
    private String connectionURL;

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
        Properties properties = new Properties();
        try (InputStream stream = PropertyService.class.getClassLoader().getResourceAsStream(CONFIG_FILE_PATH)) {
            properties.load(stream);

            daoFactoryClassName = properties.getProperty("DAOFactoryClassName");
            jdbcDriverClassName = properties.getProperty("JDBCDriverClassName");
            connectionURL = properties.getProperty("ConnectionURL");
        } catch (IOException e) {
            System.err.printf("Ошибка загрузки файла конфигурации '%s'%n", CONFIG_FILE_PATH);
            throw new IOException(e);
        }
    }

    /**
     * @return полное имя класса реализации DAOFactory
     */
    public String getDAOFactoryClassName() {
        return daoFactoryClassName;
    }

    /**
     * @return полное имя класса используемого JDBC драйвера
     */
    public String getJDBCDriverClassName() {
        return jdbcDriverClassName;
    }

    /**
     * @return URL базы данных в формате jdbc:subprotocol:subname
     */
    public String getConnectionURL() {
        return connectionURL;
    }
}
