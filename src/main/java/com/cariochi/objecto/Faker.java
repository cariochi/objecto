package com.cariochi.objecto;

import com.cariochi.objecto.repeatable.Fakers;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The {@code Faker} annotation is used to specify data generation methods and locales for fields.
 * <p>
 * This annotation allows you to define a field name, a data generation expression, and an optional locale for generating random data using the Datafaker library.
 * <p>
 * For more information on Datafaker, visit the
 * <a href="https://www.datafaker.net/documentation/getting-started/">Datafaker Documentation</a>.
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
@Repeatable(Fakers.class)
@Inherited
public @interface Faker {

    /**
     * Specifies the name of the field for which data will be generated.
     *
     * @return the field name
     */
    String field() default "";

    /**
     * Specifies the Faker expression to be used for generating data.
     * <p>
     * A large number of core expressions are available directly in this annotation, grouped by categories and providers.
     * <p>
     * For more details and examples of usage, refer to therefer to the <a href="https://www.datafaker.net/documentation/expressions/">Faker Expressions
     * Documentation</a>.
     *
     * @return the Faker expression
     */
    String expression() default "";

    /**
     * Specifies the locale to be used for data generation.
     * <p>
     * If not specified, the default locale will be used.
     *
     * @return the locale
     */
    String locale() default "";

    interface Base {

        interface Address {
            String BUILDING_NUMBER = "#{numerify '#####'}";
            String CITY = "#{address.city}";
            String CITY_NAME = "#{address.cityName}";
            String CITY_PREFIX = "#{address.cityPrefix}";
            String CITY_SUFFIX = "#{address.citySuffix}";
            String COUNTRY = "#{address.country}";
            String COUNTRY_CODE = "#{address.countryCode}";
            String FULL_ADDRESS = "#{address.fullAddress}";
            String LAT_LON = "#{address.latLon}";
            String LATITUDE = "#{address.latitude}";
            String LON_LAT = "#{address.lonLat}";
            String LONGITUDE = "#{address.longitude}";
            String MAIL_BOX = "#{numerify 'PO Box ###'}";
            String POSTCODE = "#{numerify '#####'}";
            String SECONDARY_ADDRESS = "#{numerify 'Apt. Ø##'}";
            String STATE = "#{address.state}";
            String STATE_ABBR = "#{address.stateAbbr}";
            String STREET_ADDRESS = "#{address.streetAddress}";
            String STREET_ADDRESS_NUMBER = "#{address.streetAddressNumber}";
            String STREET_NAME = "#{address.streetName}";
            String STREET_PREFIX = "#{address.streetPrefix}";
            String STREET_SUFFIX = "#{address.streetSuffix}";
            String TIME_ZONE = "#{address.timeZone}";
            String ZIP_CODE = "#{address.zipCode}";
            String ZIP_CODE_PLUS4 = "#{address.zipCodePlus4}";
        }

        interface Ancient {
            String GOD = "#{ancient.god}";
            String HERO = "#{ancient.hero}";
            String PRIMORDIAL = "#{ancient.primordial}";
            String TITAN = "#{ancient.titan}";
        }

        interface Animal {
            String GENUS = "#{animal.genus}";
            String NAME = "#{animal.name}";
            String SCIENTIFIC_NAME = "#{animal.scientificName}";
            String SPECIES = "#{animal.species}";
        }

        interface App {
            String AUTHOR = "#{app.author}";
            String NAME = "#{app.name}";
            String VERSION = "#{numerify '#.##'}";
        }

        interface Appliance {
            String BRAND = "#{appliance.brand}";
            String EQUIPMENT = "#{appliance.equipment}";
        }

        interface Artist {
            String NAME = "#{artist.name}";
        }

        interface Australia {
            String ANIMALS = "#{australia.animals}";
            String LOCATIONS = "#{australia.locations}";
            String STATES = "#{australia.states}";
        }

        interface Aviation {
            String METAR = "#{aviation.METAR}";
            String AIRLINE = "#{aviation.airline}";
            String AIRPLANE = "#{aviation.airplane}";
            String AIRPORT = "#{aviation.airport}";
            String AIRPORT_NAME = "#{aviation.airportName}";
            String ARMY_HELICOPTER = "#{aviation.armyHelicopter}";
            String CARGO = "#{aviation.cargo}";
            String CIVIL_HELICOPTER = "#{aviation.civilHelicopter}";
            String ENGINE_TYPE = "#{aviation.engineType}";
            String FLIGHT = "#{aviation.flight}";
            String FLIGHT_STATUS = "#{aviation.flightStatus}";
            String GATE = "#{aviation.gate}";
            String GENERAL = "#{aviation.general}";
            String MANUFACTURER = "#{aviation.manufacturer}";
            String SPECIAL_TYPE_DESIGNATOR = "#{aviation.specialTypeDesignator}";
            String WARPLANE = "#{aviation.warplane}";
        }

        interface Aws {
            String ACCOUNT_ID = "#{aws.accountId}";
            String ACM_ARN = "#{aws.acmARN}";
            String ALB_ARN = "#{aws.albARN}";
            String ALB_TARGET_GROUP_A_R_N = "#{aws.albTargetGroupARN}";
            String REGION = "#{aws.region}";
            String ROUTE53_ZONE_ID = "#{aws.route53ZoneId}";
            String SECURITY_GROUP_ID = "#{aws.securityGroupId}";
            String SERVICE = "#{aws.service}";
            String SUBNET_ID = "#{aws.subnetId}";
            String VPC_ID = "#{aws.vpcId}";
        }

        interface Azure {
            String APP_SERVICE_ENVIRONMENT = "#{azure.appServiceEnvironment}";
            String APP_SERVICE_PLAN = "#{azure.appServicePlan}";
            String APPLICATION_GATEWAY = "#{azure.applicationGateway}";
            String BASTION_HOST = "#{azure.bastionHost}";
            String CONTAINER_APPS = "#{azure.containerApps}";
            String CONTAINER_APPS_ENVIRONMENT = "#{azure.containerAppsEnvironment}";
            String CONTAINER_INSTANCE = "#{azure.containerInstance}";
            String CONTAINER_REGISTRY = "#{azure.containerRegistry}";
            String COSMOS_D_B_DATABASE = "#{azure.cosmosDBDatabase}";
            String FIREWALL = "#{azure.firewall}";
            String KEY_VAULT = "#{azure.keyVault}";
            String LOAD_BALANCER = "#{azure.loadBalancer}";
            String LOAD_TESTING = "#{azure.loadTesting}";
            String LOG_ANALYTICS = "#{azure.logAnalytics}";
            String MANAGEMENT_GROUP = "#{azure.managementGroup}";
            String MYSQL_DATABASE = "#{azure.mysqlDatabase}";
            String NETWORK_SECURITY_GROUP = "#{azure.networkSecurityGroup}";
            String POSTGRE_S_Q_L_DATABASE = "#{azure.postgreSQLDatabase}";
            String REGION = "#{azure.region}";
            String RESOURCE_GROUP = "#{azure.resourceGroup}";
            String SERVICE_BUS = "#{azure.serviceBus}";
            String SERVICE_BUS_QUEUE = "#{azure.serviceBusQueue}";
            String SERVICE_BUS_TOPIC = "#{azure.serviceBusTopic}";
            String SPRING_APPS = "#{azure.springApps}";
            String SQL_DATABASE = "#{azure.sqlDatabase}";
            String STATIC_WEB_APP = "#{azure.staticWebApp}";
            String STORAGE_ACCOUNT = "#{azure.storageAccount}";
            String SUBSCRIPTION_ID = "#{azure.subscriptionId}";
            String TENANT_ID = "#{azure.tenantId}";
            String VIRTUAL_MACHINE = "#{azure.virtualMachine}";
            String VIRTUAL_NETWORK = "#{azure.virtualNetwork}";
            String VIRTUAL_WAN = "#{azure.virtualWan}";
        }

        interface Barcode {
            String EAN13 = "#{barcode.ean13}";
            String EAN8 = "#{barcode.ean8}";
            String GTIN12 = "#{barcode.gtin12}";
            String GTIN13 = "#{barcode.gtin13}";
            String GTIN14 = "#{barcode.gtin14}";
            String GTIN8 = "#{barcode.gtin8}";
            String TYPE = "#{barcode.type}";
        }

        interface BloodType {
            String ABO_TYPES = "#{bloodtype.aboTypes}";
            String BLOOD_GROUP = "#{bloodtype.bloodGroup}";
            String P_TYPES = "#{bloodtype.pTypes}";
            String RH_TYPES = "#{bloodtype.rhTypes}";
        }

        interface Book {
            String AUTHOR = "#{book.author}";
            String GENRE = "#{book.genre}";
            String PUBLISHER = "#{book.publisher}";
            String TITLE = "#{book.title}";
        }

        interface Bool {
            String BOOL = "#{bool.bool}";
        }

        interface Brand {
            String CAR = "#{brand.car}";
            String SPORT = "#{brand.sport}";
            String WATCH = "#{brand.watch}";
        }

        interface Business {
            String CREDIT_CARD_EXPIRY = "#{business.creditCardExpiry}";
            String CREDIT_CARD_NUMBER = "#{business.creditCardNumber}";
            String CREDIT_CARD_TYPE = "#{business.creditCardType}";
            String SECURITY_CODE = "#{numerify '###'}";
        }

        interface CNPJ {
            String INVALID = "#{cnpj.invalid}";
            String VALID = "#{cnpj.valid}";
        }

        interface CPF {
            String INVALID = "#{cpf.invalid}";
            String VALID = "#{cpf.valid}";
        }

        interface Camera {
            String BRAND = "#{camera.brand}";
            String BRAND_WITH_MODEL = "#{camera.brandWithModel}";
            String MODEL = "#{camera.model}";
        }

        interface Cannabis {
            String BRANDS = "#{cannabis.brands}";
            String BUZZWORDS = "#{cannabis.buzzwords}";
            String CANNABINOID_ABBREVIATIONS = "#{cannabis.cannabinoidAbbreviations}";
            String CANNABINOIDS = "#{cannabis.cannabinoids}";
            String CATEGORIES = "#{cannabis.categories}";
            String HEALTH_BENEFITS = "#{cannabis.healthBenefits}";
            String MEDICAL_USES = "#{cannabis.medicalUses}";
            String STRAINS = "#{cannabis.strains}";
            String TERPENES = "#{cannabis.terpenes}";
            String TYPES = "#{cannabis.types}";
        }

        interface Cat {
            String BREED = "#{cat.breed}";
            String NAME = "#{cat.name}";
            String REGISTRY = "#{cat.registry}";
        }

        interface Chiquito {
            String EXPRESSIONS = "#{chiquito.expressions}";
            String JOKES = "#{chiquito.jokes}";
            String SENTENCES = "#{chiquito.sentences}";
            String TERMS = "#{chiquito.terms}";
        }

        interface Code {
            String ASIN = "#{code.asin}";
            String EAN13 = "#{code.ean13}";
            String EAN8 = "#{code.ean8}";
            String GTIN13 = "#{code.gtin13}";
            String GTIN8 = "#{code.gtin8}";
            String IMEI = "#{code.imei}";
            String ISBN10 = "#{code.isbn10}";
            String ISBN13 = "#{code.isbn13}";
            String ISBN_GROUP = "#{code.isbnGroup}";
            String ISBN_GS1 = "#{code.isbnGs1}";
            String ISBN_REGISTRANT = "#{code.isbnRegistrant}";
        }

        interface Coin {
            String FLIP = "#{coin.flip}";
        }

        interface Color {
            String HEX = "#{color.hex}";
            String NAME = "#{color.name}";
        }

        interface Commerce {
            String BRAND = "#{commerce.brand}";
            String DEPARTMENT = "#{commerce.department}";
            String MATERIAL = "#{commerce.material}";
            String PRICE = "#{commerce.price}";
            String VENDOR = "#{commerce.vendor}";
        }

        interface Community {
            String CHARACTER = "#{community.character}";
            String QUOTE = "#{community.quote}";
        }

        interface Company {
            String BUZZWORD = "#{company.buzzword}";
            String CATCH_PHRASE = "#{company.catchPhrase}";
            String INDUSTRY = "#{company.industry}";
            String LOGO = "#{company.logo}";
            String NAME = "#{company.name}";
            String PROFESSION = "#{company.profession}";
            String SUFFIX = "#{company.suffix}";
            String URL = "#{company.url}";
        }

        interface Compass {
            String ABBREVIATION = "#{compass.abbreviation}";
            String AZIMUTH = "#{compass.azimuth}";
            String WORD = "#{compass.word}";
        }

        interface Computer {
            String BRAND = "#{computer.brand}";
            String LINUX = "#{computer.linux}";
            String MACOS = "#{computer.macos}";
            String OPERATING_SYSTEM = "#{computer.operatingSystem}";
            String PLATFORM = "#{computer.platform}";
            String TYPE = "#{computer.type}";
            String WINDOWS = "#{computer.windows}";
        }

        interface Construction {
            String HEAVY_EQUIPMENT = "#{construction.heavyEquipment}";
            String MATERIALS = "#{construction.materials}";
            String ROLES = "#{construction.roles}";
            String STANDARD_COST_CODES = "#{construction.standardCostCodes}";
            String SUBCONTRACT_CATEGORIES = "#{construction.subcontractCategories}";
            String TRADES = "#{construction.trades}";
        }

        interface Cosmere {
            String ALLOMANCERS = "#{cosmere.allomancers}";
            String AONS = "#{cosmere.aons}";
            String FERUCHEMISTS = "#{cosmere.feruchemists}";
            String HERALDS = "#{cosmere.heralds}";
            String KNIGHTS_RADIANT = "#{cosmere.knightsRadiant}";
            String METALS = "#{cosmere.metals}";
            String SHARD_WORLDS = "#{cosmere.shardWorlds}";
            String SHARDS = "#{cosmere.shards}";
            String SPRENS = "#{cosmere.sprens}";
            String SURGES = "#{cosmere.surges}";
        }

        interface Country {
            String CAPITAL = "#{country.capital}";
            String COUNTRY_CODE2 = "#{country.countryCode2}";
            String COUNTRY_CODE3 = "#{country.countryCode3}";
            String CURRENCY = "#{country.currency}";
            String CURRENCY_CODE = "#{country.currencyCode}";
            String FLAG = "#{country.flag}";
            String NAME = "#{country.name}";
        }

        interface CryptoCoin {
            String COIN = "#{cryptocoin.coin}";
        }

        interface CultureSeries {
            String BOOKS = "#{cultureseries.books}";
            String CIVS = "#{cultureseries.civs}";
            String CULTURE_SHIP_CLASS_ABVS = "#{cultureseries.cultureShipClassAbvs}";
            String CULTURE_SHIP_CLASSES = "#{cultureseries.cultureShipClasses}";
            String CULTURE_SHIPS = "#{cultureseries.cultureShips}";
            String PLANETS = "#{cultureseries.planets}";
        }

        interface Currency {
            String CODE = "#{currency.code}";
            String NAME = "#{currency.name}";
        }

        interface DcComics {
            String HERO = "#{dccomics.hero}";
            String HEROINE = "#{dccomics.heroine}";
            String NAME = "#{dccomics.name}";
            String TITLE = "#{dccomics.title}";
            String VILLAIN = "#{dccomics.villain}";
        }

        interface Demographic {
            String DEMONYM = "#{demographic.demonym}";
            String EDUCATIONAL_ATTAINMENT = "#{demographic.educationalAttainment}";
            String MARITAL_STATUS = "#{demographic.maritalStatus}";
            String RACE = "#{demographic.race}";
            String SEX = "#{demographic.sex}";
        }

        interface Device {
            String MANUFACTURER = "#{device.manufacturer}";
            String MODEL_NAME = "#{device.modelName}";
            String PLATFORM = "#{device.platform}";
            String SERIAL = "#{device.serial}";
        }

        interface Dog {
            String AGE = "#{dog.age}";
            String BREED = "#{dog.breed}";
            String COAT_LENGTH = "#{dog.coatLength}";
            String GENDER = "#{dog.gender}";
            String MEME_PHRASE = "#{dog.memePhrase}";
            String NAME = "#{dog.name}";
            String SIZE = "#{dog.size}";
            String SOUND = "#{dog.sound}";
        }

        interface DungeonsAndDragons {
            String ALIGNMENTS = "#{dungeonsanddragons.alignments}";
            String BACKGROUNDS = "#{dungeonsanddragons.backgrounds}";
            String CITIES = "#{dungeonsanddragons.cities}";
            String KLASSES = "#{dungeonsanddragons.klasses}";
            String LANGUAGES = "#{dungeonsanddragons.languages}";
            String MELEE_WEAPONS = "#{dungeonsanddragons.meleeWeapons}";
            String MONSTERS = "#{dungeonsanddragons.monsters}";
            String RACES = "#{dungeonsanddragons.races}";
            String RANGED_WEAPONS = "#{dungeonsanddragons.rangedWeapons}";
        }

        interface Educator {
            String CAMPUS = "#{educator.campus}";
            String COURSE = "#{educator.course}";
            String SECONDARY_SCHOOL = "#{educator.secondarySchool}";
            String SUBJECT_WITH_NUMBER = "#{educator.subjectWithNumber}";
            String UNIVERSITY = "#{educator.university}";
        }

        interface ElectricalComponents {
            String ACTIVE = "#{electricalcomponents.active}";
            String ELECTROMECHANICAL = "#{electricalcomponents.electromechanical}";
            String PASSIVE = "#{electricalcomponents.passive}";
        }

        interface Emoji {
            String CAT = "#{emoji.cat}";
            String SMILEY = "#{emoji.smiley}";
            String VEHICLE = "#{emoji.vehicle}";
        }

        interface FamousLastWords {
            String LAST_WORDS = "#{famouslastwords.lastWords}";
        }

        interface File {
            String EXTENSION = "#{file.extension}";
            String FILE_NAME = "#{file.fileName}";
            String MIME_TYPE = "#{file.mimeType}";
        }

        interface Finance {
            String BIC = "#{finance.bic}";
            String IBAN = "#{finance.iban}";
            String NASDAQ_TICKER = "#{finance.nasdaqTicker}";
            String NYSE_TICKER = "#{finance.nyseTicker}";
            String STOCK_MARKET = "#{finance.stockMarket}";
            String US_ROUTING_NUMBER = "#{finance.usRoutingNumber}";
        }

        interface FinancialTerms {
            String ADJECTIVE = "#{financialterms.adjective}";
            String NOUN = "#{financialterms.noun}";
            String VERB = "#{financialterms.verb}";
        }

        interface FunnyName {
            String NAME = "#{funnyname.name}";
        }

        interface GarmentSize {
            String SIZE = "#{garmentsize.size}";
        }

        interface Gender {
            String BINARY_TYPES = "#{gender.binaryTypes}";
            String SHORT_BINARY_TYPES = "#{gender.shortBinaryTypes}";
            String TYPES = "#{gender.types}";
        }

        interface GreekPhilosopher {
            String NAME = "#{greekphilosopher.name}";
            String QUOTE = "#{greekphilosopher.quote}";
        }

        interface Hacker {
            String ABBREVIATION = "#{hacker.abbreviation}";
            String ADJECTIVE = "#{hacker.adjective}";
            String INGVERB = "#{hacker.ingverb}";
            String NOUN = "#{hacker.noun}";
            String VERB = "#{hacker.verb}";
        }

        interface Hashing {
            String MD2 = "#{hashing.md2}";
            String MD5 = "#{hashing.md5}";
            String SHA1 = "#{hashing.sha1}";
            String SHA256 = "#{hashing.sha256}";
            String SHA384 = "#{hashing.sha384}";
            String SHA512 = "#{hashing.sha512}";
        }

        interface Hipster {
            String WORD = "#{hipster.word}";
        }

        interface Hobby {
            String ACTIVITY = "#{hobby.activity}";
        }

        interface Hololive {
            String TALENT = "#{hololive.talent}";
        }

        interface Horse {
            String BREED = "#{horse.breed}";
            String NAME = "#{horse.name}";
        }

        interface House {
            String FURNITURE = "#{house.furniture}";
            String ROOM = "#{house.room}";
        }

        interface IdNumber {
            String IN_VALID_EN_ZA_SSN = "#{idnumber.inValidEnZaSsn}";
            String INVALID = "#{idnumber.invalid}";
            String INVALID_ES_M_X_SSN = "#{idnumber.invalidEsMXSsn}";
            String INVALID_PT_NIF = "#{idnumber.invalidPtNif}";
            String INVALID_SV_SE_SSN = "#{idnumber.invalidSvSeSsn}";
            String PESEL_NUMBER = "#{idnumber.peselNumber}";
            String SINGAPOREAN_FIN = "#{idnumber.singaporeanFin}";
            String SINGAPOREAN_FIN_BEFORE2000 = "#{idnumber.singaporeanFinBefore2000}";
            String SINGAPOREAN_UIN = "#{idnumber.singaporeanUin}";
            String SINGAPOREAN_UIN_BEFORE2000 = "#{idnumber.singaporeanUinBefore2000}";
            String SSN_VALID = "#{idnumber.ssnValid}";
            String VALID = "#{idnumber.valid}";
            String VALID_EN_ZA_SSN = "#{idnumber.validEnZaSsn}";
            String VALID_ES_M_X_SSN = "#{idnumber.validEsMXSsn}";
            String VALID_GE_I_D_NUMBER = "#{idnumber.validGeIDNumber}";
            String VALID_KO_KR_RRN = "#{idnumber.validKoKrRrn}";
            String VALID_PT_NIF = "#{idnumber.validPtNif}";
            String VALID_SV_SE_SSN = "#{idnumber.validSvSeSsn}";
            String VALID_ZH_C_N_SSN = "#{idnumber.validZhCNSsn}";
        }

        interface Image {
            String BASE64_BMP = "#{image.base64BMP}";
            String BASE64_GIF = "#{image.base64GIF}";
            String BASE64_JPEG = "#{image.base64JPEG}";
            String BASE64_JPG = "#{image.base64JPG}";
            String BASE64_PNG = "#{image.base64PNG}";
            String BASE64_SVG = "#{image.base64SVG}";
            String BASE64_TIFF = "#{image.base64TIFF}";
        }

        interface IndustrySegments {
            String INDUSTRY = "#{industrysegments.industry}";
            String SECTOR = "#{industrysegments.sector}";
            String SUB_SECTOR = "#{industrysegments.subSector}";
            String SUPER_SECTOR = "#{industrysegments.superSector}";
        }

        interface Internet {
            String BOT_USER_AGENT_ANY = "#{internet.botUserAgentAny}";
            String DOMAIN_NAME = "#{internet.domainName}";
            String DOMAIN_SUFFIX = "#{internet.domainSuffix}";
            String DOMAIN_WORD = "#{internet.domainWord}";
            String EMAIL_ADDRESS = "#{internet.emailAddress}";
            String EMAIL_SUBJECT = "#{internet.emailSubject}";
            String GET_IP_V4_ADDRESS = "#{internet.getIpV4Address}";
            String GET_IP_V6_ADDRESS = "#{internet.getIpV6Address}";
            String GET_PRIVATE_IP_V4_ADDRESS = "#{internet.getPrivateIpV4Address}";
            String GET_PUBLIC_IP_V4_ADDRESS = "#{internet.getPublicIpV4Address}";
            String HTTP_METHOD = "#{internet.httpMethod}";
            String IMAGE = "#{internet.image}";
            String IP_V4_ADDRESS = "#{internet.ipV4Address}";
            String IP_V4_CIDR = "#{internet.ipV4Cidr}";
            String IP_V6_ADDRESS = "#{internet.ipV6Address}";
            String IP_V6_CIDR = "#{internet.ipV6Cidr}";
            String MAC_ADDRESS = "#{internet.macAddress}";
            String PASSWORD = "#{internet.password}";
            String PORT = "#{internet.port}";
            String PRIVATE_IP_V4_ADDRESS = "#{internet.privateIpV4Address}";
            String PUBLIC_IP_V4_ADDRESS = "#{internet.publicIpV4Address}";
            String SAFE_EMAIL_ADDRESS = "#{internet.safeEmailAddress}";
            String SLUG = "#{internet.slug}";
            String URL = "#{internet.url}";
            String USERNAME = "#{internet.username}";
            String UUID = "#{internet.uuid}";
            String UUIDV3 = "#{internet.uuidv3}";
            String UUIDV4 = "#{internet.uuidv4}";
            String UUIDV7 = "#{internet.uuidv7}";
            String WEBDOMAIN = "#{internet.webdomain}";
        }

        interface Job {
            String FIELD = "#{job.field}";
            String KEY_SKILLS = "#{job.keySkills}";
            String POSITION = "#{job.position}";
            String SENIORITY = "#{job.seniority}";
            String TITLE = "#{job.title}";
        }

        interface Kpop {
            String BOY_BANDS = "#{kpop.boyBands}";
            String GIRL_GROUPS = "#{kpop.girlGroups}";
            String I_GROUPS = "#{kpop.iGroups}";
            String II_GROUPS = "#{kpop.iiGroups}";
            String III_GROUPS = "#{kpop.iiiGroups}";
            String SOLO = "#{kpop.solo}";
        }

        interface LanguageCode {
            String ISO639 = "#{languagecode.iso639}";
        }

        interface LargeLanguageModel {
            String EMBEDDING_MODEL = "#{largelanguagemodel.embeddingModel}";
            String TEXT_MODEL = "#{largelanguagemodel.textModel}";
            String TOKENIZER = "#{largelanguagemodel.tokenizer}";
        }

        interface Locality {
            String DISPLAY_NAME = "#{locality.displayName}";
            String LOCALE_STRING = "#{locality.localeString}";
            String LOCALE_STRING_WITHOUT_REPLACEMENT = "#{locality.localeStringWithoutReplacement}";
        }

        interface Location {
            String BUILDING = "#{location.building}";
            String NATURE = "#{location.nature}";
            String OTHERWORLDLY = "#{location.otherworldly}";
            String PRIVATE_SPACE = "#{location.privateSpace}";
            String PUBLIC_SPACE = "#{location.publicSpace}";
            String WORK = "#{location.work}";
        }

        interface Lorem {
            String CHARACTER = "#{lorem.character}";
            String PARAGRAPH = "#{lorem.paragraph}";
            String SENTENCE = "#{lorem.sentence}";
            String SUPPLEMENTAL = "#{lorem.supplemental}";
            String WORD = "#{lorem.word}";
            String WORDS = "#{lorem.words}";
        }

        interface Marketing {
            String BUZZWORDS = "#{marketing.buzzwords}";
        }

        interface Matz {
            String QUOTE = "#{matz.quote}";
        }

        interface Mbti {
            String CHARACTERISTIC = "#{mbti.characteristic}";
            String MERIT = "#{mbti.merit}";
            String NAME = "#{mbti.name}";
            String PERSONAGE = "#{mbti.personage}";
            String TYPE = "#{mbti.type}";
            String WEAKNESS = "#{mbti.weakness}";
        }

        interface Measurement {
            String HEIGHT = "#{measurement.height}";
            String LENGTH = "#{measurement.length}";
            String METRIC_HEIGHT = "#{measurement.metricHeight}";
            String METRIC_LENGTH = "#{measurement.metricLength}";
            String METRIC_VOLUME = "#{measurement.metricVolume}";
            String METRIC_WEIGHT = "#{measurement.metricWeight}";
            String VOLUME = "#{measurement.volume}";
            String WEIGHT = "#{measurement.weight}";
        }

        interface Medical {
            String DISEASE_NAME = "#{medical.diseaseName}";
            String HOSPITAL_NAME = "#{medical.hospitalName}";
            String MEDICAL_PROFESSION = "#{medical.medicalProfession}";
            String MEDICINE_NAME = "#{medical.medicineName}";
            String SYMPTOMS = "#{medical.symptoms}";
        }

        interface Military {
            String AIR_FORCE_RANK = "#{military.airForceRank}";
            String ARMY_RANK = "#{military.armyRank}";
            String DOD_PAYGRADE = "#{military.dodPaygrade}";
            String MARINES_RANK = "#{military.marinesRank}";
            String NAVY_RANK = "#{military.navyRank}";
        }

        interface Money {
            String CURRENCY = "#{money.currency}";
            String CURRENCY_CODE = "#{money.currencyCode}";
            String CURRENCY_NUMERIC_CODE = "#{money.currencyNumericCode}";
            String CURRENCY_SYMBOL = "#{money.currencySymbol}";
        }

        interface Mood {
            String EMOTION = "#{mood.emotion}";
            String FEELING = "#{mood.feeling}";
            String TONE = "#{mood.tone}";
        }

        interface Mountain {
            String NAME = "#{mountain.name}";
            String RANGE = "#{mountain.range}";
        }

        interface Mountaineering {
            String MOUNTAINEER = "#{mountaineering.mountaineer}";
        }

        interface Music {
            String CHORD = "#{music.chord}";
            String GENRE = "#{music.genre}";
            String INSTRUMENT = "#{music.instrument}";
            String KEY = "#{music.key}";
        }

        interface Name {
            String FEMALE_FIRST_NAME = "#{name.femaleFirstName}";
            String FIRST_NAME = "#{name.firstName}";
            String FULL_NAME = "#{name.fullName}";
            String LAST_NAME = "#{name.lastName}";
            String MALEFIRST_NAME = "#{name.malefirstName}";
            String NAME = "#{name.name}";
            String NAME_WITH_MIDDLE = "#{name.nameWithMiddle}";
            String PREFIX = "#{name.prefix}";
            String SUFFIX = "#{name.suffix}";
            String USERNAME = "#{name.username}";
        }

        interface Nation {
            String CAPITAL_CITY = "#{nation.capitalCity}";
            String ISO_COUNTRY = "#{nation.isoCountry}";
            String ISO_LANGUAGE = "#{nation.isoLanguage}";
            String LANGUAGE = "#{nation.language}";
            String NATIONALITY = "#{nation.nationality}";
        }

        interface NatoPhoneticAlphabet {
            String CODE_WORD = "#{natophoneticalphabet.codeWord}";
        }

        interface Nigeria {
            String CELEBRITIES = "#{nigeria.celebrities}";
            String FOOD = "#{nigeria.food}";
            String NAME = "#{nigeria.name}";
            String PLACES = "#{nigeria.places}";
            String SCHOOLS = "#{nigeria.schools}";
        }

        interface Number {
            String DIGIT = "#{number.digit}";
            String NEGATIVE = "#{number.negative}";
            String POSITIVE = "#{number.positive}";
            String RANDOM_DIGIT = "#{number.randomDigit}";
            String RANDOM_DIGIT_NOT_ZERO = "#{number.randomDigitNotZero}";
            String RANDOM_NUMBER = "#{number.randomNumber}";
        }

        interface OlympicSport {
            String ANCIENT_OLYMPICS = "#{olympicsport.ancientOlympics}";
            String SUMMER_OLYMPICS = "#{olympicsport.summerOlympics}";
            String SUMMER_PARALYMPICS = "#{olympicsport.summerParalympics}";
            String UNUSUAL = "#{olympicsport.unusual}";
            String WINTER_OLYMPICS = "#{olympicsport.winterOlympics}";
            String WINTER_PARALYMPICS = "#{olympicsport.winterParalympics}";
        }

        interface Passport {
            String VALID = "#{passport.valid}";
        }

        interface PhoneNumber {
            String CELL_PHONE = "#{phonenumber.cellPhone}";
            String CELL_PHONE_INTERNATIONAL = "#{phonenumber.cellPhoneInternational}";
            String EXTENSION = "#{phonenumber.extension}";
            String PHONE_NUMBER = "#{phonenumber.phoneNumber}";
            String PHONE_NUMBER_INTERNATIONAL = "#{phonenumber.phoneNumberInternational}";
            String PHONE_NUMBER_NATIONAL = "#{phonenumber.phoneNumberNational}";
            String SUBSCRIBER_NUMBER = "#{phonenumber.subscriberNumber}";
        }

        interface Photography {
            String APERTURE = "#{photography.aperture}";
            String BRAND = "#{photography.brand}";
            String CAMERA = "#{photography.camera}";
            String GENRE = "#{photography.genre}";
            String IMAGE_TAG = "#{photography.imageTag}";
            String ISO = "#{photography.iso}";
            String LENS = "#{photography.lens}";
            String SHUTTER = "#{photography.shutter}";
            String TERM = "#{photography.term}";
        }

        interface Planet {
            String GRAVITATION_PARAMETERS = "#{planet.gravitationParameters}";
            String NAME = "#{planet.name}";
        }

        interface ProgrammingLanguage {
            String CREATOR = "#{programminglanguage.creator}";
            String NAME = "#{programminglanguage.name}";
        }

        interface Pronouns {
            String OBJECTIVE = "#{pronouns.objective}";
            String POSSESSIVE = "#{pronouns.possessive}";
            String REFLEXIVE = "#{pronouns.reflexive}";
            String SUBJECTIVE = "#{pronouns.subjective}";
        }

        interface Relationship {
            String IN_LAW = "#{relationship.inLaw}";
            String PARENT = "#{relationship.parent}";
            String SIBLING = "#{relationship.sibling}";
            String SPOUSE = "#{relationship.spouse}";
        }

        interface Restaurant {
            String DESCRIPTION = "#{restaurant.description}";
            String NAME = "#{restaurant.name}";
            String NAME_PREFIX = "#{restaurant.namePrefix}";
            String NAME_SUFFIX = "#{restaurant.nameSuffix}";
            String REVIEW = "#{restaurant.review}";
            String TYPE = "#{restaurant.type}";
        }

        interface Robin {
            String QUOTE = "#{robin.quote}";
        }

        interface RockBand {
            String NAME = "#{rockband.name}";
        }

        interface Science {
            String BOSONS = "#{science.bosons}";
            String ELEMENT = "#{science.element}";
            String ELEMENT_SYMBOL = "#{science.elementSymbol}";
            String LEPTONS = "#{science.leptons}";
            String QUARK = "#{science.quark}";
            String SCIENTIST = "#{science.scientist}";
            String TOOL = "#{science.tool}";
            String UNIT = "#{science.unit}";
        }

        interface Shakespeare {
            String AS_YOU_LIKE_IT_QUOTE = "#{shakespeare.asYouLikeItQuote}";
            String HAMLET_QUOTE = "#{shakespeare.hamletQuote}";
            String KING_RICHARD_I_I_I_QUOTE = "#{shakespeare.kingRichardIIIQuote}";
            String ROMEO_AND_JULIET_QUOTE = "#{shakespeare.romeoAndJulietQuote}";
        }

        interface Sip {
            String BODY_BYTES = "#{sip.bodyBytes}";
            String BODY_STRING = "#{sip.bodyString}";
            String CLIENT_ERROR_RESPONSE_CODE = "#{sip.clientErrorResponseCode}";
            String CLIENT_ERROR_RESPONSE_PHRASE = "#{sip.clientErrorResponsePhrase}";
            String CONTENT_TYPE = "#{sip.contentType}";
            String GLOBAL_ERROR_RESPONSE_CODE = "#{sip.globalErrorResponseCode}";
            String GLOBAL_ERROR_RESPONSE_PHRASE = "#{sip.globalErrorResponsePhrase}";
            String MESSAGING_PORT = "#{sip.messagingPort}";
            String METHOD = "#{sip.method}";
            String NAME_ADDRESS = "#{sip.nameAddress}";
            String PROVISIONAL_RESPONSE_CODE = "#{sip.provisionalResponseCode}";
            String PROVISIONAL_RESPONSE_PHRASE = "#{sip.provisionalResponsePhrase}";
            String REDIRECT_RESPONSE_CODE = "#{sip.redirectResponseCode}";
            String REDIRECT_RESPONSE_PHRASE = "#{sip.redirectResponsePhrase}";
            String RTP_PORT = "#{sip.rtpPort}";
            String SERVER_ERROR_RESPONSE_CODE = "#{sip.serverErrorResponseCode}";
            String SERVER_ERROR_RESPONSE_PHRASE = "#{sip.serverErrorResponsePhrase}";
            String SUCCESS_RESPONSE_CODE = "#{sip.successResponseCode}";
            String SUCCESS_RESPONSE_PHRASE = "#{sip.successResponsePhrase}";
        }

        interface Size {
            String ADJECTIVE = "#{size.adjective}";
        }

        interface SlackEmoji {
            String ACTIVITY = "#{slackemoji.activity}";
            String CELEBRATION = "#{slackemoji.celebration}";
            String CUSTOM = "#{slackemoji.custom}";
            String EMOJI = "#{slackemoji.emoji}";
            String FOOD_AND_DRINK = "#{slackemoji.foodAndDrink}";
            String NATURE = "#{slackemoji.nature}";
            String OBJECTS_AND_SYMBOLS = "#{slackemoji.objectsAndSymbols}";
            String PEOPLE = "#{slackemoji.people}";
            String TRAVEL_AND_PLACES = "#{slackemoji.travelAndPlaces}";
        }

        interface Space {
            String AGENCY = "#{space.agency}";
            String AGENCY_ABBREVIATION = "#{space.agencyAbbreviation}";
            String COMPANY = "#{space.company}";
            String CONSTELLATION = "#{space.constellation}";
            String DISTANCE_MEASUREMENT = "#{space.distanceMeasurement}";
            String GALAXY = "#{space.galaxy}";
            String METEORITE = "#{space.meteorite}";
            String MOON = "#{space.moon}";
            String NASA_SPACE_CRAFT = "#{space.nasaSpaceCraft}";
            String NEBULA = "#{space.nebula}";
            String PLANET = "#{space.planet}";
            String STAR = "#{space.star}";
            String STAR_CLUSTER = "#{space.starCluster}";
        }

        interface Stock {
            String EXCHANGES = "#{stock.exchanges}";
            String LSE_SYMBOL = "#{stock.lseSymbol}";
            String NSDQ_SYMBOL = "#{stock.nsdqSymbol}";
            String NSE_SYMBOL = "#{stock.nseSymbol}";
            String NYSE_SYMBOL = "#{stock.nyseSymbol}";
        }

        interface Subscription {
            String PAYMENT_METHODS = "#{subscription.paymentMethods}";
            String PAYMENT_TERMS = "#{subscription.paymentTerms}";
            String PLANS = "#{subscription.plans}";
            String STATUSES = "#{subscription.statuses}";
            String SUBSCRIPTION_TERMS = "#{subscription.subscriptionTerms}";
        }

        interface Superhero {
            String DESCRIPTOR = "#{superhero.descriptor}";
            String NAME = "#{superhero.name}";
            String POWER = "#{superhero.power}";
            String PREFIX = "#{superhero.prefix}";
            String SUFFIX = "#{superhero.suffix}";
        }

        interface Team {
            String CREATURE = "#{team.creature}";
            String NAME = "#{team.name}";
            String SPORT = "#{team.sport}";
            String STATE = "#{team.state}";
        }

        interface Text {
            String CHARACTER = "#{text.character}";
            String LOWERCASE_CHARACTER = "#{text.lowercaseCharacter}";
            String TEXT = "#{text.text}";
            String UPPERCASE_CHARACTER = "#{text.uppercaseCharacter}";
        }

        interface TimeAndDate {
            String BIRTHDAY = "#{timeanddate.birthday}";
            String FUTURE = "#{timeanddate.future}";
            String PAST = "#{timeanddate.past}";
        }

        interface Tire {
            String ASPECT_RATIO = "#{tire.aspectRatio}";
            String CODE = "#{tire.code}";
            String CONSTRUCTION = "#{tire.construction}";
            String LOAD_INDEX = "#{tire.loadIndex}";
            String RIM_SIZE = "#{tire.rimSize}";
            String SPEEDRATING = "#{tire.speedrating}";
            String VEHICLE_TYPE = "#{tire.vehicleType}";
            String WIDTH = "#{tire.width}";
        }

        interface Transport {
            String TYPE = "#{transport.type}";
        }

        interface Twitter {
            String USER_ID = "#{twitter.userId}";
            String USER_NAME = "#{twitter.userName}";
        }

        interface University {
            String DEGREE = "#{university.degree}";
            String NAME = "#{university.name}";
            String PLACE = "#{university.place}";
            String PREFIX = "#{university.prefix}";
            String SUFFIX = "#{university.suffix}";
        }

        interface Vehicle {
            String CAR_OPTIONS = "#{vehicle.carOptions}";
            String CAR_TYPE = "#{vehicle.carType}";
            String COLOR = "#{vehicle.color}";
            String DOORS = "#{vehicle.doors}";
            String DRIVE_TYPE = "#{vehicle.driveType}";
            String ENGINE = "#{vehicle.engine}";
            String FUEL_TYPE = "#{vehicle.fuelType}";
            String LICENSE_PLATE = "#{bothify '???-####'}";
            String MAKE = "#{vehicle.make}";
            String MAKE_AND_MODEL = "#{vehicle.makeAndModel}";
            String MANUFACTURER = "#{vehicle.manufacturer}";
            String MODEL = "#{vehicle.model}";
            String STANDARD_SPECS = "#{vehicle.standardSpecs}";
            String STYLE = "#{vehicle.style}";
            String TRANSMISSION = "#{vehicle.transmission}";
            String UPHOLSTERY = "#{vehicle.upholstery}";
            String UPHOLSTERY_COLOR = "#{vehicle.upholsteryColor}";
            String UPHOLSTERY_FABRIC = "#{vehicle.upholsteryFabric}";
            String VIN = "#{vehicle.vin}";
        }

        interface Verb {
            String BASE = "#{verb.base}";
            String ING_FORM = "#{verb.ingForm}";
            String PAST = "#{verb.past}";
            String PAST_PARTICIPLE = "#{verb.pastParticiple}";
            String SIMPLE_PRESENT = "#{verb.simplePresent}";
        }

        interface Weather {
            String DESCRIPTION = "#{weather.description}";
            String TEMPERATURE_CELSIUS = "#{weather.temperatureCelsius}";
            String TEMPERATURE_FAHRENHEIT = "#{weather.temperatureFahrenheit}";
        }

        interface Word {
            String ADJECTIVE = "#{word.adjective}";
            String ADVERB = "#{word.adverb}";
            String CONJUNCTION = "#{word.conjunction}";
            String INTERJECTION = "#{word.interjection}";
            String NOUN = "#{word.noun}";
            String PREPOSITION = "#{word.preposition}";
            String VERB = "#{word.verb}";
        }

        interface Yoda {
            String QUOTE = "#{yoda.quote}";
        }

        interface Zodiac {
            String SIGN = "#{zodiac.sign}";
        }
    }

    interface Entertainment {

        interface AquaTeenHungerForce {
            String CHARACTER = "#{aquateenhungerforce.character}";
        }

        interface Avatar {
            String IMAGE = "#{avatar.image}";
        }

        interface Babylon5 {
            String CHARACTER = "#{babylon5.character}";
            String QUOTE = "#{babylon5.quote}";
        }

        interface BackToTheFuture {
            String CHARACTER = "#{backtothefuture.character}";
            String DATE = "#{backtothefuture.date}";
            String QUOTE = "#{backtothefuture.quote}";
        }

        interface BigBangTheory {
            String CHARACTER = "#{bigbangtheory.character}";
            String QUOTE = "#{bigbangtheory.quote}";
        }

        interface Boardgame {
            String ARTIST = "#{boardgame.artist}";
            String CATEGORY = "#{boardgame.category}";
            String DESIGNER = "#{boardgame.designer}";
            String MECHANIC = "#{boardgame.mechanic}";
            String NAME = "#{boardgame.name}";
            String PUBLISHER = "#{boardgame.publisher}";
            String SUBDOMAIN = "#{boardgame.subdomain}";
        }

        interface BojackHorseman {
            String CHARACTERS = "#{bojackhorseman.characters}";
            String QUOTES = "#{bojackhorseman.quotes}";
            String TONGUE_TWISTERS = "#{bojackhorseman.tongueTwisters}";
        }

        interface BossaNova {
            String ARTIST = "#{bossanova.artist}";
            String SONG = "#{bossanova.song}";
        }

        interface BreakingBad {
            String CHARACTER = "#{breakingbad.character}";
            String EPISODE = "#{breakingbad.episode}";
        }

        interface BrooklynNineNine {
            String CHARACTERS = "#{brooklynninenine.characters}";
            String QUOTES = "#{brooklynninenine.quotes}";
        }

        interface Buffy {
            String BIG_BADS = "#{buffy.bigBads}";
            String CELEBRITIES = "#{buffy.celebrities}";
            String CHARACTERS = "#{buffy.characters}";
            String EPISODES = "#{buffy.episodes}";
            String QUOTES = "#{buffy.quotes}";
        }

        interface ChuckNorris {
            String FACT = "#{chucknorris.fact}";
        }

        interface CowboyBebop {
            String CHARACTER = "#{cowboybebop.character}";
            String EPISODE = "#{cowboybebop.episode}";
            String QUOTE = "#{cowboybebop.quote}";
            String SONG = "#{cowboybebop.song}";
        }

        interface Departed {
            String ACTOR = "#{departed.actor}";
            String CHARACTER = "#{departed.character}";
            String QUOTE = "#{departed.quote}";
        }

        interface DetectiveConan {
            String CHARACTERS = "#{detectiveconan.characters}";
            String GADGETS = "#{detectiveconan.gadgets}";
            String VEHICLES = "#{detectiveconan.vehicles}";
        }

        interface DoctorWho {
            String ACTOR = "#{doctorwho.actor}";
            String CATCH_PHRASE = "#{doctorwho.catchPhrase}";
            String CHARACTER = "#{doctorwho.character}";
            String DOCTOR = "#{doctorwho.doctor}";
            String QUOTE = "#{doctorwho.quote}";
            String SPECIES = "#{doctorwho.species}";
            String VILLAIN = "#{doctorwho.villain}";
        }

        interface Doraemon {
            String CHARACTER = "#{doraemon.character}";
            String GADGET = "#{doraemon.gadget}";
            String LOCATION = "#{doraemon.location}";
        }

        interface DragonBall {
            String CHARACTER = "#{dragonball.character}";
        }

        interface DumbAndDumber {
            String ACTOR = "#{dumbanddumber.actor}";
            String CHARACTER = "#{dumbanddumber.character}";
            String QUOTE = "#{dumbanddumber.quote}";
        }

        interface Dune {
            String CHARACTER = "#{dune.character}";
            String PLANET = "#{dune.planet}";
            String QUOTE = "#{dune.quote}";
            String SAYING = "#{dune.saying}";
            String TITLE = "#{dune.title}";
        }

        interface FamilyGuy {
            String CHARACTER = "#{familyguy.character}";
            String LOCATION = "#{familyguy.location}";
            String QUOTE = "#{familyguy.quote}";
        }

        interface FinalSpace {
            String CHARACTER = "#{finalspace.character}";
            String QUOTE = "#{finalspace.quote}";
            String VEHICLE = "#{finalspace.vehicle}";
        }

        interface FreshPrinceOfBelAir {
            String CELEBRITIES = "#{freshprinceofbelair.celebrities}";
            String CHARACTERS = "#{freshprinceofbelair.characters}";
            String QUOTES = "#{freshprinceofbelair.quotes}";
        }

        interface Friends {
            String CHARACTER = "#{friends.character}";
            String LOCATION = "#{friends.location}";
            String QUOTE = "#{friends.quote}";
        }

        interface FullmetalAlchemist {
            String CHARACTER = "#{fullmetalalchemist.character}";
            String CITY = "#{fullmetalalchemist.city}";
            String COUNTRY = "#{fullmetalalchemist.country}";
        }

        interface Futurama {
            String CHARACTER = "#{futurama.character}";
            String HERMES_CATCH_PHRASE = "#{futurama.hermesCatchPhrase}";
            String LOCATION = "#{futurama.location}";
            String QUOTE = "#{futurama.quote}";
        }

        interface GameOfThrones {
            String CHARACTER = "#{gameofthrones.character}";
            String CITY = "#{gameofthrones.city}";
            String DRAGON = "#{gameofthrones.dragon}";
            String HOUSE = "#{gameofthrones.house}";
            String QUOTE = "#{gameofthrones.quote}";
        }

        interface Ghostbusters {
            String ACTOR = "#{ghostbusters.actor}";
            String CHARACTER = "#{ghostbusters.character}";
            String QUOTE = "#{ghostbusters.quote}";
        }

        interface GratefulDead {
            String PLAYERS = "#{gratefuldead.players}";
            String SONGS = "#{gratefuldead.songs}";
        }

        interface HarryPotter {
            String BOOK = "#{harrypotter.book}";
            String CHARACTER = "#{harrypotter.character}";
            String HOUSE = "#{harrypotter.house}";
            String LOCATION = "#{harrypotter.location}";
            String QUOTE = "#{harrypotter.quote}";
            String SPELL = "#{harrypotter.spell}";
        }

        interface HeyArnold {
            String CHARACTERS = "#{heyarnold.characters}";
            String LOCATIONS = "#{heyarnold.locations}";
            String QUOTES = "#{heyarnold.quotes}";
        }

        interface HitchhikersGuideToTheGalaxy {
            String CHARACTER = "#{hitchhikersguidetothegalaxy.character}";
            String LOCATION = "#{hitchhikersguidetothegalaxy.location}";
            String MARVIN_QUOTE = "#{hitchhikersguidetothegalaxy.marvinQuote}";
            String PLANET = "#{hitchhikersguidetothegalaxy.planet}";
            String QUOTE = "#{hitchhikersguidetothegalaxy.quote}";
            String SPECIES = "#{hitchhikersguidetothegalaxy.species}";
            String STARSHIP = "#{hitchhikersguidetothegalaxy.starship}";
        }

        interface Hobbit {
            String CHARACTER = "#{hobbit.character}";
            String LOCATION = "#{hobbit.location}";
            String QUOTE = "#{hobbit.quote}";
            String THORINS_COMPANY = "#{hobbit.thorinsCompany}";
        }

        interface HowIMetYourMother {
            String CATCH_PHRASE = "#{howimetyourmother.catchPhrase}";
            String CHARACTER = "#{howimetyourmother.character}";
            String HIGH_FIVE = "#{howimetyourmother.highFive}";
            String QUOTE = "#{howimetyourmother.quote}";
        }

        interface HowToTrainYourDragon {
            String CHARACTERS = "#{howtotrainyourdragon.characters}";
            String DRAGONS = "#{howtotrainyourdragon.dragons}";
            String LOCATIONS = "#{howtotrainyourdragon.locations}";
        }

        interface Joke {
            String KNOCK_KNOCK = "#{joke.knockKnock}";
            String PUN = "#{joke.pun}";
        }

        interface Kaamelott {
            String CHARACTER = "#{kaamelott.character}";
            String QUOTE = "#{kaamelott.quote}";
        }

        interface Lebowski {
            String ACTOR = "#{lebowski.actor}";
            String CHARACTER = "#{lebowski.character}";
            String QUOTE = "#{lebowski.quote}";
        }

        interface LordOfTheRings {
            String CHARACTER = "#{lordoftherings.character}";
            String LOCATION = "#{lordoftherings.location}";
        }

        interface MoneyHeist {
            String CHARACTER = "#{moneyheist.character}";
            String HEIST = "#{moneyheist.heist}";
            String QUOTE = "#{moneyheist.quote}";
        }

        interface Movie {
            String NAME = "#{movie.name}";
            String QUOTE = "#{movie.quote}";
        }

        interface Naruto {
            String CHARACTER = "#{naruto.character}";
            String DEMON = "#{naruto.demon}";
            String EYE = "#{naruto.eye}";
            String VILLAGE = "#{naruto.village}";
        }

        interface NewGirl {
            String CHARACTERS = "#{newgirl.characters}";
            String QUOTES = "#{newgirl.quotes}";
        }

        interface OnePiece {
            String AKUMAS_NO_MI = "#{onepiece.akumasNoMi}";
            String CHARACTER = "#{onepiece.character}";
            String ISLAND = "#{onepiece.island}";
            String LOCATION = "#{onepiece.location}";
            String QUOTE = "#{onepiece.quote}";
            String SEA = "#{onepiece.sea}";
        }

        interface OscarMovie {
            String ACTOR = "#{oscarmovie.actor}";
            String CHARACTER = "#{oscarmovie.character}";
            String GET_CHOICE = "#{oscarmovie.getChoice}";
            String GET_YEAR = "#{oscarmovie.getYear}";
            String MOVIE_NAME = "#{oscarmovie.movieName}";
            String QUOTE = "#{oscarmovie.quote}";
            String RELEASE_DATE = "#{oscarmovie.releaseDate}";
        }

        interface Pokemon {
            String LOCATION = "#{pokemon.location}";
            String MOVE = "#{pokemon.move}";
            String NAME = "#{pokemon.name}";
            String TYPE = "#{pokemon.type}";
        }

        interface PrincessBride {
            String CHARACTER = "#{princessbride.character}";
            String QUOTE = "#{princessbride.quote}";
        }

        interface ResidentEvil {
            String BIOLOGICAL_AGENT = "#{residentevil.biologicalAgent}";
            String CHARACTER = "#{residentevil.character}";
            String CREATURE = "#{residentevil.creature}";
            String EQUIPMENT = "#{residentevil.equipment}";
            String LOCATION = "#{residentevil.location}";
        }

        interface RickAndMorty {
            String CHARACTER = "#{rickandmorty.character}";
            String LOCATION = "#{rickandmorty.location}";
            String QUOTE = "#{rickandmorty.quote}";
        }

        interface RuPaulDragRace {
            String QUEEN = "#{rupauldragrace.queen}";
            String QUOTE = "#{rupauldragrace.quote}";
        }

        interface Seinfeld {
            String BUSINESS = "#{seinfeld.business}";
            String CHARACTER = "#{seinfeld.character}";
            String QUOTE = "#{seinfeld.quote}";
        }

        interface Show {
            String ADULT_MUSICAL = "#{show.adultMusical}";
            String KIDS_MUSICAL = "#{show.kidsMusical}";
            String PLAY = "#{show.play}";
        }

        interface SiliconValley {
            String APP = "#{siliconvalley.app}";
            String CHARACTER = "#{siliconvalley.character}";
            String COMPANY = "#{siliconvalley.company}";
            String EMAIL = "#{siliconvalley.email}";
            String INVENTION = "#{siliconvalley.invention}";
            String MOTTO = "#{siliconvalley.motto}";
            String QUOTE = "#{siliconvalley.quote}";
            String URL = "#{siliconvalley.url}";
        }

        interface Simpsons {
            String CHARACTER = "#{simpsons.character}";
            String LOCATION = "#{simpsons.location}";
            String QUOTE = "#{simpsons.quote}";
        }

        interface SouthPark {
            String CHARACTERS = "#{southpark.characters}";
            String QUOTES = "#{southpark.quotes}";
        }

        interface Spongebob {
            String CHARACTERS = "#{spongebob.characters}";
            String EPISODES = "#{spongebob.episodes}";
            String QUOTES = "#{spongebob.quotes}";
        }

        interface StarTrek {
            String CHARACTER = "#{startrek.character}";
            String KLINGON = "#{startrek.klingon}";
            String LOCATION = "#{startrek.location}";
            String SPECIES = "#{startrek.species}";
            String VILLAIN = "#{startrek.villain}";
        }

        interface StarWars {
            String ALTERNATE_CHARACTER_SPELLING = "#{starwars.alternateCharacterSpelling}";
            String CALL_SIGN = "#{starwars.callSign}";
            String CHARACTER = "#{starwars.character}";
            String DROIDS = "#{starwars.droids}";
            String PLANETS = "#{starwars.planets}";
            String QUOTES = "#{starwars.quotes}";
            String SPECIES = "#{starwars.species}";
            String VEHICLES = "#{starwars.vehicles}";
            String WOOKIE_WORDS = "#{starwars.wookieWords}";
        }

        interface Stargate {
            String CHARACTERS = "#{stargate.characters}";
            String PLANETS = "#{stargate.planets}";
            String QUOTES = "#{stargate.quotes}";
        }

        interface StrangerThings {
            String CHARACTER = "#{strangerthings.character}";
            String QUOTE = "#{strangerthings.quote}";
        }

        interface StudioGhibli {
            String CHARACTER = "#{studioghibli.character}";
            String MOVIE = "#{studioghibli.movie}";
            String QUOTE = "#{studioghibli.quote}";
        }

        interface Suits {
            String CHARACTERS = "#{suits.characters}";
            String QUOTES = "#{suits.quotes}";
        }

        interface Supernatural {
            String CHARACTER = "#{supernatural.character}";
            String CREATURE = "#{supernatural.creature}";
            String WEAPON = "#{supernatural.weapon}";
        }

        interface SwordArtOnline {
            String GAME_NAME = "#{swordartonline.gameName}";
            String ITEM = "#{swordartonline.item}";
            String LOCATION = "#{swordartonline.location}";
            String REAL_NAME = "#{swordartonline.realName}";
        }

        interface TheExpanse {
            String CHARACTERS = "#{theexpanse.characters}";
            String LOCATIONS = "#{theexpanse.locations}";
            String QUOTES = "#{theexpanse.quotes}";
            String SHIPS = "#{theexpanse.ships}";
        }

        interface TheItCrowd {
            String ACTORS = "#{theitcrowd.actors}";
            String CHARACTERS = "#{theitcrowd.characters}";
            String EMAILS = "#{theitcrowd.emails}";
            String QUOTES = "#{theitcrowd.quotes}";
        }

        interface TheKingkillerChronicle {
            String BOOK = "#{thekingkillerchronicle.book}";
            String CHARACTER = "#{thekingkillerchronicle.character}";
            String CREATURE = "#{thekingkillerchronicle.creature}";
            String LOCATION = "#{thekingkillerchronicle.location}";
        }

        interface TheRoom {
            String ACTORS = "#{theroom.actors}";
            String CHARACTERS = "#{theroom.characters}";
            String LOCATIONS = "#{theroom.locations}";
            String QUOTES = "#{theroom.quotes}";
        }

        interface TheThickOfIt {
            String CHARACTERS = "#{thethickofit.characters}";
            String DEPARTMENTS = "#{thethickofit.departments}";
            String POSITIONS = "#{thethickofit.positions}";
        }

        interface TheVentureBros {
            String CHARACTER = "#{theventurebros.character}";
            String ORGANIZATION = "#{theventurebros.organization}";
            String QUOTE = "#{theventurebros.quote}";
            String VEHICLE = "#{theventurebros.vehicle}";
        }

        interface Tron {
            String ALTERNATE_CHARACTER_SPELLING = "#{tron.alternateCharacterSpelling}";
            String CHARACTER = "#{tron.character}";
            String GAME = "#{tron.game}";
            String LOCATION = "#{tron.location}";
            String QUOTE = "#{tron.quote}";
            String TAGLINE = "#{tron.tagline}";
            String VEHICLE = "#{tron.vehicle}";
        }

        interface TwinPeaks {
            String CHARACTER = "#{twinpeaks.character}";
            String LOCATION = "#{twinpeaks.location}";
            String QUOTE = "#{twinpeaks.quote}";
        }

        interface VForVendetta {
            String CHARACTERS = "#{vforvendetta.characters}";
            String QUOTES = "#{vforvendetta.quotes}";
            String SPEECHES = "#{vforvendetta.speeches}";
        }

        interface Witcher {
            String BOOK = "#{witcher.book}";
            String CHARACTER = "#{witcher.character}";
            String LOCATION = "#{witcher.location}";
            String MONSTER = "#{witcher.monster}";
            String POTION = "#{witcher.potion}";
            String QUOTE = "#{witcher.quote}";
            String SCHOOL = "#{witcher.school}";
            String SIGN = "#{witcher.sign}";
            String WITCHER = "#{witcher.witcher}";
        }
    }

    interface FoodAndDrinks {

        interface Beer {
            String BRAND = "#{beer.brand}";
            String HOP = "#{beer.hop}";
            String MALT = "#{beer.malt}";
            String NAME = "#{beer.name}";
            String STYLE = "#{beer.style}";
            String YEAST = "#{beer.yeast}";
        }

        interface Coffee {
            String BODY = "#{coffee.body}";
            String COUNTRY = "#{coffee.country}";
            String DESCRIPTOR = "#{coffee.descriptor}";
            String INTENSIFIER = "#{coffee.intensifier}";
            String NAME1 = "#{coffee.name1}";
            String NAME2 = "#{coffee.name2}";
            String REGION = "#{coffee.region}";
            String VARIETY = "#{coffee.variety}";
        }

        interface Dessert {
            String FLAVOR = "#{dessert.flavor}";
            String TOPPING = "#{dessert.topping}";
            String VARIETY = "#{dessert.variety}";
        }

        interface Food {
            String ALLERGEN = "#{food.allergen}";
            String DISH = "#{food.dish}";
            String FRUIT = "#{food.fruit}";
            String INGREDIENT = "#{food.ingredient}";
            String MEASUREMENT = "#{food.measurement}";
            String SPICE = "#{food.spice}";
            String SUSHI = "#{food.sushi}";
            String VEGETABLE = "#{food.vegetable}";
        }

        interface Tea {
            String TYPE = "#{tea.type}";
        }
    }

    interface Healthcare {

        interface CareProvider {
            String HOSPITAL_NAME = "#{careprovider.hospitalName}";
            String MEDICAL_PROFESSION = "#{careprovider.medicalProfession}";
        }

        interface Disease {
            String ANY_DISEASE = "#{disease.anyDisease}";
            String DERMATOLOGY = "#{disease.dermatology}";
            String GYNECOLOGY_AND_OBSTETRICS = "#{disease.gynecologyAndObstetrics}";
            String ICD10 = "#{disease.icd10}";
            String INTERNAL_DISEASE = "#{disease.internalDisease}";
            String NEUROLOGY = "#{disease.neurology}";
            String OPHTHALMOLOGY_AND_OTORHINOLARYNGOLOGY = "#{disease.ophthalmologyAndOtorhinolaryngology}";
            String PAEDIATRICS = "#{disease.paediatrics}";
            String SURGERY = "#{disease.surgery}";
        }

        interface MedicalProcedure {
            String ICD10 = "#{medicalprocedure.icd10}";
        }

        interface Medication {
            String DRUG_NAME = "#{medication.drugName}";
        }

        interface Observation {
            String SYMPTOM = "#{observation.symptom}";
        }
    }

    interface Sport {

        interface Baseball {
            String COACHES = "#{baseball.coaches}";
            String PLAYERS = "#{baseball.players}";
            String POSITIONS = "#{baseball.positions}";
            String TEAMS = "#{baseball.teams}";
        }

        interface Basketball {
            String COACHES = "#{basketball.coaches}";
            String PLAYERS = "#{basketball.players}";
            String POSITIONS = "#{basketball.positions}";
            String TEAMS = "#{basketball.teams}";
        }

        interface Chess {
            String OPENING = "#{chess.opening}";
            String PLAYER = "#{chess.player}";
            String TITLE = "#{chess.title}";
            String TOURNAMENT = "#{chess.tournament}";
        }

        interface Cricket {
            String FORMATS = "#{cricket.formats}";
            String PLAYERS = "#{cricket.players}";
            String TEAMS = "#{cricket.teams}";
            String TOURNAMENTS = "#{cricket.tournaments}";
        }

        interface EnglandFootBall {
            String LEAGUE = "#{englandfootball.league}";
            String TEAM = "#{englandfootball.team}";
        }

        interface Football {
            String COACHES = "#{football.coaches}";
            String COMPETITIONS = "#{football.competitions}";
            String PLAYERS = "#{football.players}";
            String POSITIONS = "#{football.positions}";
            String TEAMS = "#{football.teams}";
        }

        interface Formula1 {
            String CIRCUIT = "#{formula1.circuit}";
            String DRIVER = "#{formula1.driver}";
            String GRAND_PRIX = "#{formula1.grandPrix}";
            String TEAM = "#{formula1.team}";
        }

        interface Volleyball {
            String COACH = "#{volleyball.coach}";
            String FORMATION = "#{volleyball.formation}";
            String PLAYER = "#{volleyball.player}";
            String POSITION = "#{volleyball.position}";
            String TEAM = "#{volleyball.team}";
        }
    }

    interface Videogame {

        interface Battlefield1 {
            String CLASSES = "#{battlefield1.classes}";
            String FACTION = "#{battlefield1.faction}";
            String MAP = "#{battlefield1.map}";
            String VEHICLE = "#{battlefield1.vehicle}";
            String WEAPON = "#{battlefield1.weapon}";
        }

        interface ClashOfClans {
            String DEFENSIVE_BUILDING = "#{clashofclans.defensiveBuilding}";
            String RANK = "#{clashofclans.rank}";
            String TROOP = "#{clashofclans.troop}";
        }

        interface Control {
            String ALTERED_ITEM = "#{control.alteredItem}";
            String ALTERED_WORLD_EVENT = "#{control.alteredWorldEvent}";
            String CHARACTER = "#{control.character}";
            String HISS = "#{control.hiss}";
            String LOCATION = "#{control.location}";
            String OBJECT_OF_POWER = "#{control.objectOfPower}";
            String QUOTE = "#{control.quote}";
            String THE_BOARD = "#{control.theBoard}";
        }

        interface DarkSouls {
            String CLASSES = "#{darksouls.classes}";
            String COVENANTS = "#{darksouls.covenants}";
            String SHIELD = "#{darksouls.shield}";
            String STATS = "#{darksouls.stats}";
        }

        interface Dota2 {
            String ATTRIBUTE = "#{dota2.attribute}";
            String BUILDING = "#{dota2.building}";
            String FACTION = "#{dota2.faction}";
            String HERO = "#{dota2.hero}";
            String ITEM = "#{dota2.item}";
            String NEUTRAL_ITEM = "#{dota2.neutralItem}";
            String PLAYER = "#{dota2.player}";
            String RANK = "#{dota2.rank}";
            String TEAM = "#{dota2.team}";
            String TIER = "#{dota2.tier}";
        }

        interface EldenRing {
            String LOCATION = "#{eldenring.location}";
            String NPC = "#{eldenring.npc}";
            String SKILL = "#{eldenring.skill}";
            String SPELL = "#{eldenring.spell}";
            String WEAPON = "#{eldenring.weapon}";
        }

        interface ElderScrolls {
            String CITY = "#{elderscrolls.city}";
            String CREATURE = "#{elderscrolls.creature}";
            String DRAGON = "#{elderscrolls.dragon}";
            String FIRST_NAME = "#{elderscrolls.firstName}";
            String LAST_NAME = "#{elderscrolls.lastName}";
            String QUOTE = "#{elderscrolls.quote}";
            String RACE = "#{elderscrolls.race}";
            String REGION = "#{elderscrolls.region}";
        }

        interface Esports {
            String EVENT = "#{esports.event}";
            String GAME = "#{esports.game}";
            String LEAGUE = "#{esports.league}";
            String PLAYER = "#{esports.player}";
            String TEAM = "#{esports.team}";
        }

        interface Fallout {
            String CHARACTER = "#{fallout.character}";
            String FACTION = "#{fallout.faction}";
            String LOCATION = "#{fallout.location}";
            String QUOTE = "#{fallout.quote}";
        }

        interface FinalFantasyXIV {
            String CHARACTER = "#{finalfantasyxiv.character}";
            String DATA_CENTER = "#{finalfantasyxiv.dataCenter}";
            String JOB = "#{finalfantasyxiv.job}";
            String RACE = "#{finalfantasyxiv.race}";
            String ZONE = "#{finalfantasyxiv.zone}";
        }

        interface HalfLife {
            String CHARACTER = "#{halflife.character}";
            String ENEMY = "#{halflife.enemy}";
            String LOCATION = "#{halflife.location}";
        }

        interface Hearthstone {
            String BATTLEGROUNDS_SCORE = "#{hearthstone.battlegroundsScore}";
            String MAIN_CHARACTER = "#{hearthstone.mainCharacter}";
            String MAIN_PATTERN = "#{hearthstone.mainPattern}";
            String MAIN_PROFESSION = "#{hearthstone.mainProfession}";
            String STANDARD_RANK = "#{hearthstone.standardRank}";
            String WILD_RANK = "#{hearthstone.wildRank}";
        }

        interface HeroesOfTheStorm {
            String BATTLEGROUND = "#{heroesofthestorm.battleground}";
            String HERO = "#{heroesofthestorm.hero}";
            String HERO_CLASS = "#{heroesofthestorm.heroClass}";
            String QUOTE = "#{heroesofthestorm.quote}";
        }

        interface LeagueOfLegends {
            String CHAMPION = "#{leagueoflegends.champion}";
            String LOCATION = "#{leagueoflegends.location}";
            String MASTERIES = "#{leagueoflegends.masteries}";
            String QUOTE = "#{leagueoflegends.quote}";
            String RANK = "#{leagueoflegends.rank}";
            String SUMMONER_SPELL = "#{leagueoflegends.summonerSpell}";
        }

        interface MarvelSnap {
            String CHARACTER = "#{marvelsnap.character}";
            String EVENT = "#{marvelsnap.event}";
            String RANK = "#{marvelsnap.rank}";
            String ZONE = "#{marvelsnap.zone}";
        }

        interface MassEffect {
            String CHARACTER = "#{masseffect.character}";
            String CLUSTER = "#{masseffect.cluster}";
            String PLANET = "#{masseffect.planet}";
            String QUOTE = "#{masseffect.quote}";
            String SPECIE = "#{masseffect.specie}";
        }

        interface Minecraft {
            String ANIMAL_NAME = "#{minecraft.animalName}";
            String ENTITY_NAME = "#{minecraft.entityName}";
            String ITEM_NAME = "#{minecraft.itemName}";
            String MONSTER_NAME = "#{minecraft.monsterName}";
            String TILE_ITEM_NAME = "#{minecraft.tileItemName}";
            String TILE_NAME = "#{minecraft.tileName}";
        }

        interface Myst {
            String AGES = "#{myst.ages}";
            String CHARACTERS = "#{myst.characters}";
            String CREATURES = "#{myst.creatures}";
            String GAMES = "#{myst.games}";
            String QUOTES = "#{myst.quotes}";
        }

        interface Overwatch {
            String HERO = "#{overwatch.hero}";
            String LOCATION = "#{overwatch.location}";
            String QUOTE = "#{overwatch.quote}";
        }

        interface RedDeadRedemption2 {
            String ANIMAL = "#{reddeadredemption2.animal}";
            String GANG_MEMBER = "#{reddeadredemption2.gangMember}";
            String MAJOR_CHARACTER = "#{reddeadredemption2.majorCharacter}";
            String PROTAGONIST = "#{reddeadredemption2.protagonist}";
            String QUOTE = "#{reddeadredemption2.quote}";
            String REGION = "#{reddeadredemption2.region}";
            String SETTLEMENT = "#{reddeadredemption2.settlement}";
            String STATE = "#{reddeadredemption2.state}";
            String WEAPON = "#{reddeadredemption2.weapon}";
        }

        interface SonicTheHedgehog {
            String CHARACTER = "#{sonicthehedgehog.character}";
            String GAME = "#{sonicthehedgehog.game}";
            String ZONE = "#{sonicthehedgehog.zone}";
        }

        interface SoulKnight {
            String BOSSES = "#{soulknight.bosses}";
            String BUFFS = "#{soulknight.buffs}";
            String CHARACTERS = "#{soulknight.characters}";
            String ENEMIES = "#{soulknight.enemies}";
            String STATUES = "#{soulknight.statues}";
            String WEAPONS = "#{soulknight.weapons}";
        }

        interface StarCraft {
            String BUILDING = "#{starcraft.building}";
            String CHARACTER = "#{starcraft.character}";
            String PLANET = "#{starcraft.planet}";
            String UNIT = "#{starcraft.unit}";
        }

        interface StreetFighter {
            String CHARACTERS = "#{streetfighter.characters}";
            String MOVES = "#{streetfighter.moves}";
            String QUOTES = "#{streetfighter.quotes}";
            String STAGES = "#{streetfighter.stages}";
        }

        interface SuperMario {
            String CHARACTERS = "#{supermario.characters}";
            String GAMES = "#{supermario.games}";
            String LOCATIONS = "#{supermario.locations}";
        }

        interface SuperSmashBros {
            String FIGHTER = "#{supersmashbros.fighter}";
            String STAGE = "#{supersmashbros.stage}";
        }

        interface Touhou {
            String CHARACTER_FIRST_NAME = "#{touhou.characterFirstName}";
            String CHARACTER_LAST_NAME = "#{touhou.characterLastName}";
            String CHARACTER_NAME = "#{touhou.characterName}";
            String GAME_NAME = "#{touhou.gameName}";
            String TRACK_NAME = "#{touhou.trackName}";
        }

        interface VideoGame {
            String GENRE = "#{videogame.genre}";
            String PLATFORM = "#{videogame.platform}";
            String TITLE = "#{videogame.title}";
        }

        interface WarhammerFantasy {
            String CREATURES = "#{warhammerfantasy.creatures}";
            String FACTIONS = "#{warhammerfantasy.factions}";
            String HEROS = "#{warhammerfantasy.heros}";
            String LOCATIONS = "#{warhammerfantasy.locations}";
            String QUOTES = "#{warhammerfantasy.quotes}";
        }

        interface WorldOfWarcraft {
            String HERO = "#{worldofwarcraft.hero}";
            String QUOTES = "#{worldofwarcraft.quotes}";
        }

        interface Zelda {
            String CHARACTER = "#{zelda.character}";
        }
    }

}
