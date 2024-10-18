package com.cariochi.objecto;

/**
 * The DatafakerMethod interface contains nested interfaces that define constants for various data generation methods using the Datafaker library.
 */
public interface DatafakerMethod {

    /**
     * The Address interface contains constants for address-related data generation methods.
     */
    interface Address {
        String StreetName = "address.streetName";
        String StreetAddressNumber = "address.streetAddressNumber";
        String StreetAddress = "address.streetAddress";
        String SecondaryAddress = "address.secondaryAddress";
        String ZipCode = "address.zipCode";
        String Postcode = "address.postcode";
        String ZipCodePlus4 = "address.zipCodePlus4";
        String StreetSuffix = "address.streetSuffix";
        String StreetPrefix = "address.streetPrefix";
        String CitySuffix = "address.citySuffix";
        String CityPrefix = "address.cityPrefix";
        String City = "address.city";
        String CityName = "address.cityName";
        String State = "address.state";
        String StateAbbr = "address.stateAbbr";
        String Latitude = "address.latitude";
        String Longitude = "address.longitude";
        String LatLon = "address.latLon";
        String LonLat = "address.lonLat";
        String TimeZone = "address.timeZone";
        String Country = "address.country";
        String CountryCode = "address.countryCode";
        String BuildingNumber = "address.buildingNumber";
        String FullAddress = "address.fullAddress";
        String MailBox = "address.mailBox";
    }

    /**
     * The Avatar interface contains constants for avatar-related data generation methods.
     */
    interface Avatar {
        String Image = "avatar.image";
    }

    /**
     * The Book interface contains constants for book-related data generation methods.
     */
    interface Book {
        String Author = "book.author";
        String Title = "book.title";
        String Publisher = "book.publisher";
        String Genre = "book.genre";
    }

    /**
     * The Brand interface contains constants for brand-related data generation methods.
     */
    interface Brand {
        String Sport = "brand.sport";
        String Car = "brand.car";
        String Watch = "brand.watch";
    }

    /**
     * The Business interface contains constants for business-related data generation methods.
     */
    interface Business {
        String CreditCardExpiry = "business.creditCardExpiry";
        String CreditCardNumber = "business.creditCardNumber";
        String CreditCardType = "business.creditCardType";
        String SecurityCode = "business.securityCode";
    }

    /**
     * The Color interface contains constants for color-related data generation methods.
     */
    interface Color {
        String Hex = "color.hex";
        String Name = "color.name";
    }

    /**
     * The Commerce interface contains constants for commerce-related data generation methods.
     */
    interface Commerce {
        String Department = "commerce.department";
        String ProductName = "commerce.productName";
        String Material = "commerce.material";
        String Brand = "commerce.brand";
        String Vendor = "commerce.vendor";
        String Price = "commerce.price";
        String PromotionCode = "commerce.promotionCode";
    }

    /**
     * The Company interface contains constants for company-related data generation methods.
     */
    interface Company {
        String Name = "company.name";
        String Suffix = "company.suffix";
        String Industry = "company.industry";
        String Profession = "company.profession";
        String Buzzword = "company.buzzword";
        String CatchPhrase = "company.catchPhrase";
        String Bs = "company.bs";
        String Logo = "company.logo";
        String Url = "company.url";
    }

    /**
     * The Computer interface contains constants for computer-related data generation methods.
     */
    interface Computer {
        String Brand = "computer.brand";
        String Linux = "computer.linux";
        String Macos = "computer.macos";
        String OperatingSystem = "computer.operatingSystem";
        String Platform = "computer.platform";
        String Type = "computer.type";
        String Windows = "computer.windows";
    }

    /**
     * The Currency interface contains constants for currency-related data generation methods.
     */
    interface Currency {
        String Code = "currency.code";
        String Name = "currency.name";
    }

    /**
     * The Device interface contains constants for device-related data generation methods.
     */
    interface Device {
        String Manufacturer = "device.manufacturer";
        String ModelName = "device.modelName";
        String Platform = "device.platform";
        String Serial = "device.serial";
    }

    /**
     * The File interface contains constants for file-related data generation methods.
     */
    interface File {
        String Extension = "file.extension";
        String FileName = "file.fileName";
        String MimeType = "file.mimeType";
    }

    /**
     * The Finance interface contains constants for finance-related data generation methods.
     */
    interface Finance {
        String NasdaqTicker = "finance.nasdaqTicker";
        String NyseTicker = "finance.nyseTicker";
        String StockMarket = "finance.stockMarket";
        String CreditCard = "finance.creditCard";
        String Bic = "finance.bic";
        String Iban = "finance.iban";
        String UsRoutingNumber = "finance.usRoutingNumber";
    }

    /**
     * The Image interface contains constants for image-related data generation methods.
     */
    interface Image {
        String Base64GIF = "image.base64GIF";
        String Base64JPG = "image.base64JPG";
        String Base64PNG = "image.base64PNG";
        String Base64SVG = "image.base64SVG";
    }

    /**
     * The Internet interface contains constants for internet-related data generation methods.
     */
    interface Internet {
        String Username = "internet.username";
        String EmailAddress = "internet.emailAddress";
        String SafeEmailAddress = "internet.safeEmailAddress";
        String EmailSubject = "internet.emailSubject";
        String DomainName = "internet.domainName";
        String DomainWord = "internet.domainWord";
        String DomainSuffix = "internet.domainSuffix";
        String Url = "internet.url";
        String Webdomain = "internet.webdomain";
        String Image = "internet.image";
        String HttpMethod = "internet.httpMethod";
        String Password = "internet.password";
        String Port = "internet.port";
        String MacAddress = "internet.macAddress";
        String IpV4Address = "internet.ipV4Address";
        String PrivateIpV4Address = "internet.privateIpV4Address";
        String IpV4Cidr = "internet.ipV4Cidr";
        String IpV6Address = "internet.ipV6Address";
        String IpV6Cidr = "internet.ipV6Cidr";
        String Slug = "internet.slug";
        String Uuidv3 = "internet.uuidv3";
        String Uuidv4 = "internet.uuidv4";
        String Uuidv7 = "internet.uuidv7";
        String Uuid = "internet.uuid";
        String UserAgent = "internet.userAgent";
        String BotUserAgentAny = "internet.botUserAgentAny";
    }

    /**
     * The Lorem interface contains constants for lorem ipsum text generation methods.
     */
    interface Lorem {
        String Word = "lorem.word";
        String Sentence = "lorem.sentence";
        String Paragraph = "lorem.paragraph";
    }

    /**
     * The Medical interface contains constants for medical-related data generation methods.
     */
    interface Medical {
        String MedicineName = "medical.medicineName";
        String DiseaseName = "medical.diseaseName";
        String HospitalName = "medical.hospitalName";
        String Symptoms = "medical.symptoms";
        String DiagnosisCode = "medical.diagnosisCode";
        String ProcedureCode = "medical.procedureCode";
        String MedicalProfession = "medical.medicalProfession";
    }

    /**
     * The Name interface contains constants for name-related data generation methods.
     */
    interface Name {
        String Name = "name.name";
        String NameWithMiddle = "name.nameWithMiddle";
        String FullName = "name.fullName";
        String FirstName = "name.firstName";
        String LastName = "name.lastName";
        String Prefix = "name.prefix";
        String Suffix = "name.suffix";
        String Title = "name.title";
        String Username = "name.username";
    }

    /**
     * The PhoneNumber interface contains constants for phone number-related data generation methods.
     */
    interface PhoneNumber {
        String CountryCodeIso2 = "phoneNumber.countryCodeIso2";
        String CellPhone = "phoneNumber.cellPhone";
        String CellPhoneInternational = "phoneNumber.cellPhoneInternational";
        String PhoneNumber = "phoneNumber.phoneNumber";
        String PhoneNumberInternational = "phoneNumber.phoneNumberInternational";
        String PhoneNumberNational = "phoneNumber.phoneNumberNational";
        String Extension = "phoneNumber.extension";
        String SubscriberNumber = "phoneNumber.subscriberNumber";
    }

    /**
     * The TimeAndDate interface contains constants for time and date-related data generation methods.
     */
    interface TimeAndDate {
        String Future = "timeAndDate.future";
        String Past = "timeAndDate.past";
        String Birthday = "timeAndDate.birthday";
    }

    /**
     * The Vehicle interface contains constants for vehicle-related data generation methods.
     */
    interface Vehicle {
        String Vin = "vehicle.vin";
        String Manufacturer = "vehicle.manufacturer";
        String Make = "vehicle.make";
        String Model = "vehicle.model";
        String MakeAndModel = "vehicle.makeAndModel";
        String Style = "vehicle.style";
        String Color = "vehicle.color";
        String UpholsteryColor = "vehicle.upholsteryColor";
        String UpholsteryFabric = "vehicle.upholsteryFabric";
        String Upholstery = "vehicle.upholstery";
        String Transmission = "vehicle.transmission";
        String DriveType = "vehicle.driveType";
        String FuelType = "vehicle.fuelType";
        String CarType = "vehicle.carType";
        String Engine = "vehicle.engine";
        String Doors = "vehicle.doors";
        String LicensePlate = "vehicle.licensePlate";
    }

    /**
     * The Yoda interface contains constants for Yoda-related data generation methods.
     */
    interface Yoda {
        String Quote = "yoda.quote";
    }
}
