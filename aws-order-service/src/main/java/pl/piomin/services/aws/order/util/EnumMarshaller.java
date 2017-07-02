package pl.piomin.services.aws.order.util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;

@SuppressWarnings("rawtypes")
public class EnumMarshaller implements DynamoDBMarshaller<Enum> {
 
	@Override
    public String marshall(Enum getterReturnResult) {
        return getterReturnResult.name();
    }

    @SuppressWarnings("unchecked")
	@Override
    public Enum unmarshall(Class<Enum> clazz, String obj) {
        return Enum.valueOf(clazz, obj);
    }
}
