$: << File.dirname(__FILE__)
require '../../lib/hotspot_evil'

import Java::HotspotEvil::Address
import Java::HotspotEvil::Dereference
import Java::HotspotEvilUtil::RuntimeProperties

builder = FileBuilder.build(__FILE__) do
  package 'hotspot.evil' do
    public_class 'MarkWord', MagicAccessorImpl do  
      public_static_method :of, [], long, object do
        not_32bit = label
        not_64bit = label
        
        aload 0
        invokestatic Address, :of, [long, object]
        
        iload_arch_data_model
        bipush 32
        if_icmpne not_32bit
        invokestatic Dereference, :int32, [int, long]
        i2l
        lreturn
        
        not_32bit.set!
        iload_arch_data_model
        bipush 64
        if_icmpne not_64bit
        invokestatic Dereference, :int64, [long, long]
        lreturn
        
        not_64bit.set!
        pop2
        ldc_long 0xDAD00BADBEEF
        lreturn
      end
      
      public_static_method :klass, [], long, object do
        not_32bit = label
        not_64bit = label
        not_narrowOop = label
        
        aload 0
        invokestatic Address, :of, [long, object]
        
        iload_arch_data_model
        push_int 32
        if_icmpne not_32bit
        ldc_long 4
        ladd
        invokestatic Dereference, :int32, [int, long]
        i2l
        lreturn
        
        not_32bit.set!
        iload_arch_data_model
        push_int 64
        if_icmpne not_64bit
        ldc_long 8
        ladd
        iload_object_header_size
        push_int 12
        if_icmpne not_narrowOop
        invokestatic Dereference, :int32, [int, long]
        i2l
        lreturn
        not_narrowOop.set!
        invokestatic Dereference, :int64, [long, long]
        lreturn
        
        not_64bit.set!
        pop2
        ldc_long 0xDAD00BADBEEF
        lreturn
      end
    end
  end
end

outdir = ARGV.first || '.'

builder.generate do |filename, class_builder|
  require 'fileutils'
  FileUtils.mkdir_p(File.join(outdir, File.dirname(filename)))
  File.open(File.join(outdir, filename), 'wb') do |file|
    file.write(class_builder.generate)
  end
end

