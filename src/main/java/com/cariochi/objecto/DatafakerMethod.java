package com.cariochi.objecto;

public interface DatafakerMethod {

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

    interface TimeAndDate {
        String Future = "timeAndDate.future";
        String Past = "timeAndDate.past";
        String Birthday = "timeAndDate.birthday";
    }

    interface Avatar {
        String Image = "avatar.image";
    }

    interface Brand {
        String Sport = "brand.sport";
        String Car = "brand.car";
        String Watch = "brand.watch";
    }

    interface Business {
        String CreditCardExpiry = "business.creditCardExpiry";
        String CreditCardNumber = "business.creditCardNumber";
        String CreditCardType = "business.creditCardType";
        String SecurityCode = "business.securityCode";
    }

    interface Color {
        String Hex = "color.hex";
        String Name = "color.name";
    }

    interface Computer {
        String Brand = "computer.brand";
        String Linux = "computer.linux";
        String Macos = "computer.macos";
        String OperatingSystem = "computer.operatingSystem";
        String Platform = "computer.platform";
        String Type = "computer.type";
        String Windows = "computer.windows";
    }

    interface Currency {
        String Code = "currency.code";
        String Name = "currency.name";
    }

    interface Device {
        String Manufacturer = "device.manufacturer";
        String ModelName = "device.modelName";
        String Platform = "device.platform";
        String Serial = "device.serial";
    }

    interface File {
        String Extension = "file.extension";
        String FileName = "file.fileName";
        String MimeType = "file.mimeType";
    }

    interface Image {
        String Base64GIF = "image.base64GIF";
        String Base64JPG = "image.base64JPG";
        String Base64PNG = "image.base64PNG";
        String Base64SVG = "image.base64SVG";
    }

    interface Lorem {
        String Word = "lorem.word";
        String Sentence = "lorem.sentence";
        String Paragraph = "lorem.paragraph";
    }

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

    interface Finance {
        String NasdaqTicker = "finance.nasdaqTicker";
        String NyseTicker = "finance.nyseTicker";
        String StockMarket = "finance.stockMarket";
        String CreditCard = "finance.creditCard";
        String Bic = "finance.bic";
        String Iban = "finance.iban";
        String UsRoutingNumber = "finance.usRoutingNumber";
    }

    interface Commerce {
        String Department = "commerce.department";
        String ProductName = "commerce.productName";
        String Material = "commerce.material";
        String Brand = "commerce.brand";
        String Vendor = "commerce.vendor";
        String Price = "commerce.price";
        String PromotionCode = "commerce.promotionCode";
    }

    interface Medical {
        String MedicineName = "medical.medicineName";
        String DiseaseName = "medical.diseaseName";
        String HospitalName = "medical.hospitalName";
        String Symptoms = "medical.symptoms";
        String DiagnosisCode = "medical.diagnosisCode";
        String ProcedureCode = "medical.procedureCode";
        String MedicalProfession = "medical.medicalProfession";

    }

    interface Book {
        String Author = "book.author";
        String Title = "book.title";
        String Publisher = "book.publisher";
        String Genre = "book.genre";
    }

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

    interface Yoda {
        String Quote = "yoda.quote";
    }
}
