module HotSpotEvil
  module Macros
    def self.macro(name, &b)
      MethodBuilder.send :define_method, name, &b
    end
=begin
    macro :unsigned_isub_8 do
      i2l
      ldc_long 0xFFFFFFFF
      land
      ldc_long 8
      lsub
      l2i
    end
=end
    macro :iload_arch_data_model do
      getstatic RuntimeProperties, :VM_ARCH_DATA_MODEL, int
    end
    
    macro :iload_object_header_size do
      getstatic RuntimeProperties, :OBJECT_HEADER_SIZE, int
    end
    
    macro :lsub_object_header_size do # ltos -> ltos
      iload_object_header_size
      i2l
      lsub
    end
  end
end

