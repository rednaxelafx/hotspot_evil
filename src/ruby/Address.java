$: << File.dirname(__FILE__)
require '../../lib/hotspot_evil'

import Java::HotspotEvilUtil::RuntimeProperties

builder = FileBuilder.build(__FILE__) do
  package 'hotspot.evil' do
    public_class 'Address', MagicAccessorImpl do
      public_static_method :of, [], long, object do
        not_32bit = label
        not_64bit = label
        
        iload_arch_data_model
        bipush 32
        if_icmpne not_32bit
        iload 0
        i2l
        ldc_long 0xFFFFFFFF
        land
        lreturn
        
        not_32bit.set!
        iload_arch_data_model
        bipush 64
        if_icmpne not_64bit
        aload 0
        astore 1
        lload 0
        lreturn
        
        not_64bit.set!
        ldc_long -1
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

